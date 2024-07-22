# Build stage
FROM eclipse-temurin:22-jdk AS build
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the application files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:22-jre
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/target/lyxrica_springb-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 5454

# Set the entry point
ENTRYPOINT ["java", "-jar", "app.jar"]