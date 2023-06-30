#FROM maven:3.8.3-openjdk-17-slim AS build
#WORKDIR /app
#COPY . .
#RUN mvn clean install
#
#FROM openjdk:17-jdk-slim
#ARG JAR_FILE=target/*.jar
#COPY --from=build /app/${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

# WITHOUT mv clean install (need to be done manually)
FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]