# Stage 1: Compile and package the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Deploy in lightweight JRE container
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/BloodDonationManagementSystem-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
