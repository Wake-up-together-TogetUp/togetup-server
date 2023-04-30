FROM openjdk:11
ARG JAR_FILE=TogetUp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} TogetUp.jar
ENTRYPOINT ["java","-jar","TogetUp.jar"]