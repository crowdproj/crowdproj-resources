FROM bellsoft/liberica-openjdk-alpine:17
COPY build/libs/resources-ktor.jar /app/app.jar

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "/app/app.jar"]