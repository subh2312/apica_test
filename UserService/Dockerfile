# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn clean install -DskipTests

# Stage 2:Run the application
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/apica-user-micro.jar ./apica-user-micro.jar
EXPOSE 8083
CMD ["java", "-jar", "apica-user-micro.jar"]

