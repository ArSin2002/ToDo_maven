# Stage 1: Build the application
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files to the working directory
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean install

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged application from the builder stage
COPY --from=builder /app/target/todo-tracker-1.0-SNAPSHOT.jar app.jar

# Expose the port your application listens on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar", "server", "/app/config.yml"]
