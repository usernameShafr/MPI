FROM openjdk:8-jdk-alpine
RUN addgroup -S hatchery && adduser -S hatchery -G hatchery

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY k8s/hatchery-entrypoint.sh /hatchery-entrypoint.sh
RUN chown hatchery:hatchery /hatchery-entrypoint.sh /app.jar && chmod u+x /hatchery-entrypoint.sh

USER hatchery:hatchery
EXPOSE 8080
ENTRYPOINT ["/hatchery-entrypoint.sh"]