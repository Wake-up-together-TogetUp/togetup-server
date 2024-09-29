FROM openjdk:17
EXPOSE 9010
ARG JAR_FILE=./build/libs/TogetUp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} TogetUp.jar
ENTRYPOINT ["java","-jar","TogetUp.jar"]
