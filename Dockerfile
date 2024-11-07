FROM eclipse-temurin:21-jdk-alpine

RUN apk add bash
RUN apk add --no-cache gradle

WORKDIR /app

COPY gradlew gradlew.bat build.gradle settings.gradle ./
COPY src src

RUN gradle wrapper --gradle-version 8.3
RUN ./gradlew clean build

EXPOSE 8085 8086 1433 6379 6380 6381 9093 5671 5672 9354 9355

CMD ["java", "-jar", "build/libs/modular-monolith-0.0.1-SNAPSHOT.jar"]