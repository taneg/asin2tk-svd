FROM openjdk:8-jre-slim
MAINTAINER "kalpana"<838958665@qq.com>
ENV PARAMS=""
ADD /target/asin2tk-svd-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["/bin/sh","-c","java -server -XX:SurvivorRatio=6 -XX:NewRatio=1 -XX:+UseParallelGC -jar app.jar -Djava.security.egd=file:/dev/./urandom $PARAMS"]
