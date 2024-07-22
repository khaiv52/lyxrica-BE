# Build stage
FROM maven:3-openjdk-22 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:22-jre
WORKDIR /app

COPY --from=build /app/target/lyxrica_springb-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5454

ENTRYPOINT ["java", "-jar", "app.jar"]
