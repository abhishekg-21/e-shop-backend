# Use JDK 22 to build the Spring Boot app
FROM eclipse-temurin:22-jdk AS build

# Set work directory in container
WORKDIR /app

# Copy project files into container
COPY . .

# Build the app using Maven wrapper (without running tests)
RUN ./mvnw clean package -DskipTests

# Use a smaller image to run the app (JRE only)
FROM eclipse-temurin:22-jre

# Set working directory for runtime
WORKDIR /app

# Copy built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
