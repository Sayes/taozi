FROM adoptopenjdk/openjdk11:alpine

MAINTAINER yizhong.shen@seetatech.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir /taozi-be/config/taozi -p
RUN mkdir /taozi-be/public/excel_templates -p

WORKDIR /taozi-be

EXPOSE 8081

ADD ./src/main/resources/taozi ./config/taozi
ADD ./target/taozi-be-0.2.0.jar ./
ADD ./target/classes/public/excel_templates/Stats_result_a7_1000.xls ./public/excel_templates/
ADD ./target/classes/public/excel_templates/Stats_result_a8_1000.xls ./public/excel_templates/

CMD sleep 10;java -Djava.security.egd=file:/dev/./urandom -jar taozi-be-0.2.0.jar
