# Use Java 23 JDK base image
FROM eclipse-temurin:23-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Make the Maven wrapper executable (in case it's not already)
RUN chmod +x ./mvnw

# Package the app (skip tests during build)
RUN ./mvnw clean package -DskipTests

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the app using the built JAR (assuming there's only one)
CMD ["sh", "-c", "java -jar target/*.jar"]
