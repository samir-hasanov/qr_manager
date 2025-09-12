# Stage 1: Build with Gradle
FROM gradle:7.6.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Stage 2: Run JAR
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","app.jar"]

