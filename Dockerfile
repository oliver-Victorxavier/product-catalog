FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml first to leverage Docker layer caching.
# This way, dependencies will only be downloaded again if pom.xml changes.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and compile the application.
COPY src ./src
RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine AS runtime

# Create a dedicated user and group for the application for security (principle of least privilege).
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

WORKDIR /app

# Create a directory for logs and assign ownership to the application user.
RUN mkdir -p /app/logs && chown -R appuser:appgroup /app/logs

# Copy the .jar artifact generated in the build stage.
COPY --from=build /app/target/*.jar app.jar

# Set the non-root user as default to run the application.
USER appuser

EXPOSE 8080

# Set environment variables, including JVM optimizations for containers.
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport"

# Command to check application health.
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Command to start the application.
CMD java $JAVA_OPTS -jar app.jar