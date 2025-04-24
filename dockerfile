# Use Java 23 JDK base image
FROM eclipse-temurin:23-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml first (better caching)
COPY .mvn .mvn
COPY mvnw pom.xml ./

# Download dependencies (uses Docker cache efficiently)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Now copy the rest of the source code
COPY src ./src

# Package the app (skip tests during build)
RUN ./mvnw clean package -DskipTests

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the app using the built JAR
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]

