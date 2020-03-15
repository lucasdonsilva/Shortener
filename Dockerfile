FROM openjdk:11-alpine

MAINTAINER lucasdonsilva@gmail.com

COPY /target/shortener.jar /app/shortener.jar

CMD ["java", "-jar", "/target/shortener.jar"]