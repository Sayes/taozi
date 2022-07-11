2022-03-25

./db/taozi-mysql-5.7.sql    所有表, 包括系统表, 业务相关的表, 带数据
./db/create_tables.sql      仅业务相关的表, 只有结构不带数据
./script/csv_to_mysql.py    csv导入mysql数据库的脚本, 用法: python3 PATH_CSV TABLE_NAME, 注意csv数据和表字段,数据类型要对应

deploy 部署服务

生成后端服务镜像

cd backend

使用make生成镜像
make all active=release ver=0.2.0

清除生成的镜像
make clean active=release ver=0.2.0
