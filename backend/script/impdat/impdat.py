from asyncio import create_task
from enum import unique
from re import I
import sys
import os
from numpy import column_stack
import pymysql
import yaml
import pandas as pd
import pinyin
import numpy as np

# sys.argv[1]   yml_base_path
# sys.argv[2]   module_name
# sys.argv[3]   entity_name
# sys.argv[4]   source_csv_pathname
# sys.argv[5]   column_match_csv_pathname


class ScriptGenerator(object):

    # yml_base_path路径指到module目录上一级
    YML_BASE_PATH = ""
    MODULE_NAME = ""
    ENTITY_NAME = ""
    # 源数据csv文件路径
    SRC_CSV_PATHNAME = ""
    # 表头匹配文件路径（表头匹配文件分两行，第一行为目标数据表字段，第二行为源csv文件字段，对应的字段放在同一列）
    COLUMN_MATCH_CSV_PATHNAME = ""

    # 以下分别为目标数据表'标题','字段名','类型'
    dst_titles = []
    dst_columns = []
    dst_type = []

    # 源数据标题
    src_titles = []

    # 已匹配文件目标和源字段
    match_dst = []
    match_src = []

    # 已匹配索引，2维数组，第一维分别标识:匹配表头索引项、匹配目标表头索引项、匹配源数据表头索引项, 第二维分别表示各索引值
    matched_idx = []

    def __init__(self):
        print("__init__ called")

        if len(sys.argv) > 5:
            self.YML_BASE_PATH = sys.argv[1]
            self.MODULE_NAME = sys.argv[2]
            self.ENTITY_NAME = sys.argv[3]
            self.SRC_CSV_PATHNAME = sys.argv[4]
            self.COLUMN_MATCH_CSV_PATHNAME = sys.argv[5]
        else:
            print("usage: python3 impdat.py YML_BASE_PATH MODULE_NAME ENTITY_NAME SRC_CSV_PATHNAME COLUMN_MATCH_CSV_PATHNAME")
            exit()

    def __del__(self):
        print("__del__ called")

    # 获取字段匹配信息, 该文件目前手动生成，将来可以做成自动匹配相近字段，文件格式详见示例 column_match.xlsx(使用时转为csv)
    def read_column_match_csv(self, match_csv):
        data = pd.read_csv(match_csv, encoding="utf-8", na_filter=False, header=None)
        #data = pd.read_csv(self.src_csv_fn, encoding="gb18030", na_filter=False)

        tmp = list(data.values)
        dst = []
        src = []
        for it1 in tmp[0]:
            dst.append(it1)
        for it2 in tmp[1]:
            src.append(it2)

        for i in range (0, max(len(dst), len(src))):
            if len(dst[i]) > 0 and len(src[i]) > 0:
                self.match_dst.append(dst[i])
                self.match_src.append(src[i])
                self.matched_idx.append([i, -1, -1])

    # 获取目标数据表的yml字段信息, 标题，字段，类型
    def get_columns_from_yml(self, yml_path, module_name, entity_name):
        yml_abs_path = yml_path + os.path.sep + module_name + os.path.sep + entity_name + ".yml"
        print(yml_abs_path)
        with open(yml_abs_path, "r", encoding="utf-8") as f:
            try:
                yml_content = f.read()
                data = yaml.load(yml_content, Loader=yaml.SafeLoader)
                for it in data["column"]:
                    try:
                        self.dst_type.append(it["type"])
                        self.dst_titles.append(it["frontend"]["title"])
                        self.dst_columns.append(it["name"])
                    except:
                        if it["reference"]["type"] == "fetch":
                            continue
                        print("deal with reference: leftJoin")
                        try:
                            module_name = it["reference"]["module"]
                        except:
                            module_name = self.MODULE_NAME
                        self.dst_type.append(self.getLeftJoinType(yml_path, module_name, it["reference"]["entity"], it["reference"]["field"]))
                        self.dst_titles.append(it["frontend"]["title"])
                        self.dst_columns.append(it["name"])

            finally:
                if f:
                    f.close()

    # 检查目标数据表标题，如果column_match目标字段在yml文件不存在，报错退出
    def check_dst_columns(self):
        for it1 in self.match_dst:
            fount = False
            for it2 in self.dst_titles:
                if it1 == it2:
                    fount = True
                    continue
            if fount == True:
                continue
            else:
                print("dst titles not match with template, column: " + it1)
                exit()

    # 检查源数据表标题，如果column_match源字段在数据文件标题中不存在，报错退出
    def check_src_columns(self):
        for it1 in self.match_src:
            fount = False
            for it2 in self.src_titles:
                if it1 == it2:
                    fount = True
                    continue
            if fount == True:
                continue
            else:
                print("src titles not match with template, column: " + it1)
                exit()

    # TODO, 获取leftJoin, fetch字段类型
    def getLeftJoinType(self, yml_path, module_name, entity_name, column_name):
        yml_abs_path = yml_path + os.path.sep + module_name + os.path.sep + entity_name + ".yml"
        print(yml_abs_path)
        with open(yml_abs_path, "r", encoding="utf-8") as f:
            try:
                yml_content = f.read()
                data = yaml.load(yml_content, Loader=yaml.SafeLoader)
                for it in data["column"]:
                    if it["name"] == column_name:
                        try:
                            return it["type"]
                        except:
                            try:
                                module_name = it["reference"]["module"]
                            except:
                                module_name = self.MODULE_NAME
                            return self.getLeftJoinType(yml_path, module_name, it["reference"]["entity"], it["reference"]["field"])
            finally:
                if f:
                    f.close()

    # 读取数据写入目标表，生成SQL脚本
    def read_src_data_gen_script(self, src_csv):

        data = pd.read_csv(src_csv, encoding="utf-8", na_filter=False, header=None)
        #data = pd.read_csv(self.src_csv_fn, encoding="gb18030", na_filter=False)
        column_values = list(data.values)
        self.src_titles = column_values[0]

        self.check_src_columns()

        src_data = column_values[1:]

        for i in range (0, len(self.matched_idx)):
            for j in range (0, len(self.dst_titles)):
                if self.match_dst[i] == self.dst_titles[j]:
                    self.matched_idx[i][1] = j

        for i in range (0, len(self.matched_idx)):
            for j in range (0, len(self.src_titles)):
                if self.match_src[i] == self.src_titles[j]:
                    self.matched_idx[i][2] = j

        tmp_dst_columns = "("
        for it in self.matched_idx:
            dst_idx = it[1]
            tmp_dst_columns += self.dst_columns[dst_idx] + ","
        tmp_dst_columns = tmp_dst_columns[:-1]
        tmp_dst_columns += ")"

        insert_script = "insert into tbl_" + self.ENTITY_NAME + tmp_dst_columns + " values " + "\n"

        for it in src_data:

            tmp_values = "("
            for it2 in self.matched_idx:
                dst_idx = it2[1]
                src_idx = it2[2]

                if self.dst_type[dst_idx] == "string":
                    tmp_values += "'" + it[src_idx] + "',"
                elif self.dst_type[dst_idx] == "int" or self.dst_type[dst_idx] == "long":
                    if len(it[src_idx]) > 0:
                        tmp_values += "cast('" + it[src_idx] + "' as signed),"
                    else:
                        tmp_values += "0,"
                elif self.dst_type[dst_idx] == "decimal":
                    if len(it[src_idx]) > 0:
                        tmp_values += "cast('" + it[src_idx] + "' as decimal),"
                    else:
                        tmp_values += "0,"
                elif self.dst_type[dst_idx] == "stake":
                    if len(it[src_idx]) > 0:
                        tmp_values += "cast('" + it[src_idx] + "' as decimal)*100000,"
                    else:
                        tmp_values += "0,"
                elif self.dst_type[dst_idx] == "kilometer":
                    if len(it[src_idx]) > 0:
                        tmp_values += "cast('" + it[src_idx] + "' as decimal)*1000,"
                    else:
                        tmp_values += "0,"
                elif self.dst_type[dst_idx] == "meter":
                    if len(it[src_idx]) > 0:
                        tmp_values += "cast('" + it[src_idx] + "' as decimal)*100,"
                    else:
                        tmp_values += "0,"
                elif self.dst_type[dst_idx] == "date":
                    if len(it[src_idx]) > 4:
                        tmp_values += "cast('" + it[src_idx] + "' as datetime),"
                    else:
                        tmp_values += "cast('1971-01-01' as datetime),"
                else:
                    tmp_values += "'" + it[src_idx] + "',"

            tmp_values = tmp_values[:-1]
            tmp_values += "),"
            insert_script += tmp_values
        insert_script = insert_script[:-1]
        insert_script += ";"
        return insert_script

    def get_insert_script(self, yml_path, module_name, entity_name, src_csv, match_csv):
        self.read_column_match_csv(match_csv)
        self.get_columns_from_yml(yml_path, module_name, entity_name)
        self.check_dst_columns()
        return self.read_src_data_gen_script(src_csv)

    def run(self):
        script = self.get_insert_script(self.YML_BASE_PATH, self.MODULE_NAME, self.ENTITY_NAME, self.SRC_CSV_PATHNAME, self.COLUMN_MATCH_CSV_PATHNAME)
        print(script)
        return


def main():
    sg = ScriptGenerator()
    sg.run()


if __name__ == "__main__":
    main()
