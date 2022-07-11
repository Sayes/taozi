import os
import sys 
import yaml


def get_file_list(path, file_list):
 
    new_path = path
 
    if os.path.isfile(path):
        file_list.append(path)
 
    elif os.path.isdir(path):
        for s in os.listdir(path):
            new_path = os.path.join(path, s)
            get_file_list(new_path, file_list)
 
    return file_list


def gen_sql(fn):
    result = ""
    with open(fn, "r", encoding="utf-8") as f:
        try:
            content = f.read()
            data = yaml.load(content, Loader=yaml.SafeLoader)
            version = data.get("version")

            scheme = data.get("scheme")
            metadata = scheme.get("metadata")
            tables = scheme.get("tables")
            for it_table in range(len(tables)):
                table = tables[it_table]
                table_name = table.get("name")
                if table_name == "":
                    continue
                auto_increment = table.get("auto_increment")
                engine = table.get("engine")
                if not engine:
                    engine = "InnoDB"
                encoding = table.get("encoding")
                if not encoding:
                    encoding = "utf8"

                result += "CREATE TABLE `" + table_name + "`(\n"

                primary_key = ""
                fields = table.get("fields")
                for it_field in range(len(fields)):
                    field = fields[it_field]
                    field_name = field.get("name")
                    if field_name[0:5] == "__vv_":
                        raise Exception("reserved prefix used")
                    field_type = field.get("type")
                    field_length = field.get("length")
                    field_pk = field.get("primary_key")
                    field_nn = field.get("not_null")
                    field_ai = field.get("auto_inc")
                    field_comment = field.get("comment")
                    field_default = field.get("default")

                    result += "  `" + field_name + "` " + field_type + "(" + str(field_length) + ") "
                    if field_pk:
                        primary_key += "`" + field_name + "`,"
                    if field_nn:
                        result += " NOT NULL "
                    if field_ai:
                        result += " AUTO_INCREMENT "
                    if field_default:
                        result += " DEFAULT " + field_default
                    if field_comment:
                        result += " COMMENT '" + field_comment + "'"
                    result += ",\n"

                result += "  `__vv_create_at` datetime NOT NULL,\n"
                result += "  `__vv_update_at` datetime NOT NULL,\n"

                if primary_key != "":
                    result += "  PRIMARY KEY(" + primary_key[:-1] + ")\n"
                else:
                    result = result[:-1]

                result += ") ENGINE=" + engine
                if auto_increment:
                    result += " AUTO_INCREMENT=" + str(auto_increment)
                result += " DEFAULT CHARSET=" + encoding + ";\n\n"

        except Exception as e:
            print(e.args)
            print("load file [" + fn + "] failed")

    return result


if __name__ == '__main__':

    if len(sys.argv) > 1:

        fn_list = get_file_list(sys.argv[1], [])

        str_sql = ""
        for it in fn_list:

            if it.endswith(".yml"):
                str_sql += gen_sql(it)

        if len(sys.argv) > 2:
            fo = open(sys.argv[2], "w")
            fo.write(str_sql)
            fo.close()
        else:
            print(str_sql)
    else:
        print("Usage: python3 gen_sql_script.py [LOCAL_PATH_FOR_YAML] [LOCAL_PATH_FOR_OUTPUT]")
