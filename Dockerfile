FROM openjdk:8u191-jdk-alpine3.9
ADD build/libs/scripts-creator-frontend-production-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar scripts-creator-frontend-production-0.0.1-SNAPSHOT.jar --envname=prod