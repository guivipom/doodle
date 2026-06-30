# Stage 1: Builder
FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -q
COPY src/ src/
RUN ./mvnw package -DskipTests -q

# Stage 2: Runtime
FROM eclipse-temurin:25-jre AS runtime
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
WORKDIR /app
RUN chown appuser:appgroup /app
USER appuser
COPY --from=builder /app/target/doodle-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]