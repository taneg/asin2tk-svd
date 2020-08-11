FROM java
MAINTAINER "kalpana"<838958665@qq.com>
ADD ifast-1.0.0.jar app.jar
EXPOSE 8088
CMD java -jar app.jar --spring.profiles.active=prod
