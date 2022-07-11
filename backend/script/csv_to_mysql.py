import sys
import pymysql
import pandas as pd

# sys.argv[1]   csv file name
# sys.argv[2]   database table name
# sys.argv[3]   script file name of create table, append data if no this

# """ 配置数据库 """
HOST = "192.168.1.100"
PORT = 3306
USERNAME = "root"
PASSWORD = "tgTBesuBnL7SK0KL"
DATABASE = "DB_NAME"
ENCODING = "utf8"


# 用面向对象的方式编写，更加熟悉面向对象代码风格
class MysqlCsv(object):

    # 定义一个init方法，用于读取数据库
    def __init__(self):
        # 读取数据库和建立游标对象
        self.connect = pymysql.connect(host=HOST, port=PORT, user=USERNAME, password=PASSWORD, database=DATABASE, charset=ENCODING)
        self.cursor = self.connect.cursor()

    # 定义一个del类，用于运行完所有程序的时候关闭数据库和游标对象
    def __del__(self):
        self.connect.close()
        self.cursor.close()

    def read_csv_colnmus(self):
        # 读取csv文件的列索引，用于建立数据表时的字段
        csv_name = sys.argv[1]
        data = pd.read_csv(csv_name, encoding="utf-8")
        return data

    def read_csv_values(self):
        # 读取csv文件数据
        csv_name = sys.argv[1]
        data = pd.read_csv(csv_name, encoding="utf-8", na_filter=False)
        data_3 = list(data.values)
        return data_3

    def write_mysql(self):
        # 在数据表中写入数据，因为数据是列表类型，把他转化为元组更符合sql语句
        for i in self.read_csv_values():
            data_6 = tuple(i)
            sql = ("insert into " + sys.argv[2] + " values{}").format(data_6)
            self.cursor.execute(sql)
            self.commit()
        print("\n数据植入完成")

    def commit(self):
        # 定义一个确认事务运行
        self.connect.commit()

    def create(self):
        # 若已有数据表weather_year_db，则删除
        # query="drop table if exists tran_in_mod;"
        query = "drop table if exists " + sys.argv[2] + ";"
        self.cursor.execute(query)
        # 创建数据表，用刚才提取的列索引作为字段
        data_2 = self.read_csv_colnmus()

        with open(sys.argv[3], "r", encoding="UTF-8") as f:
            try:
                sql = f.read()
                self.cursor.execute(sql)
                self.commit()
            finally:
                if f:
                    f.close()

    # 运行程序，记得要先调用创建数据的类，在创建写入数据的类
    def run(self):
        if len(sys.argv) > 3:
            self.create()
        self.write_mysql()


# 最后用一个main()函数来封装
def main():
    if len(sys.argv) == 1:
        print("python3 csv_to_mysql.py data.csv table_name")
        return
    sql = MysqlCsv()
    sql.run()


if __name__ == "__main__":
    main()

