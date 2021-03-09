FROM openjdk:8-jdk-alpine
RUN addgroup -S hatchery && adduser -S hatchery -G hatchery
USER hatchery:hatchery
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]