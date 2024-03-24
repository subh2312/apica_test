# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn clean install -DskipTests

# Stage 2:Run the application
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/apica-service-registry.jar ./apica-service-registry.jar
EXPOSE 8081
CMD ["java", "-jar", "apica-service-registry.jar"]

