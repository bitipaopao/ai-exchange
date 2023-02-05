FROM openjdk:8u242-jre-slim

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN echo 'Asia/Shanghai' > /etc/timezone

ENV JAVA_OPTS="-Dlog4j2.formatMsgNoLookups=true"
ENV ACT_ENV="spec"

COPY ./target/ai-exchange.jar /home/ai-exchange/
COPY ./app_conf/application-spec.yml /home/ai-exchange/
COPY ./app_conf/application.yml /home/ai-exchange/

RUN sh -c 'touch /home/ai-exchange.jar'

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.edg=file:/dev/./urandom -jar /home/ai-exchange/ai-exchange.jar --spring.profiles.active=$ACT_ENV --spring.config.location=/home/ai-exchange/application.yml,/home/ai-exchange/application-spec.yml" ]

