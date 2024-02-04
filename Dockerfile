# Stage 1: Build the Spring Boot application
FROM openjdk:20 AS builder

WORKDIR /app

# Copy the necessary files for the build
COPY . .

# Change permissions for the mvnw script
RUN chmod +x mvnw

# Build the Spring Boot application
RUN ./mvnw clean install -DskipTests

# Stage 2: Create the final image
FROM openjdk:20

WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/rai-workflow.jar /app/rai-workflow.jar

# Install MySQL
RUN apt-get update && \
    apt-get install -y mysql-server && \
    service mysql start && \
    mysql -u root -e "CREATE DATABASE IF NOT EXISTS rai;"

# Expose the port for the Spring Boot application
EXPOSE 8080

# Start MySQL and the Spring Boot application
CMD service mysql start && java -jar /app/rai-workflow.jar
