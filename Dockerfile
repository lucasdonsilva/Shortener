FROM openjdk:11

MAINTAINER lucasdonsilva@gmail.com

COPY /target/shortener.jar /app/shortener.jar

CMD java $JAVA_OPTS -jar /app/shortener.jar
