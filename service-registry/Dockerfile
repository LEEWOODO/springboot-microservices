FROM eclipse-temurin:17

LABEL maintainer="dnejdzlr2@naver.com"

WORKDIR /app

# Copy the source code
COPY target/service-registry-0.0.1-SNAPSHOT.jar /app/service-registry-0.0.1-SNAPSHOT.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/service-registry-0.0.1-SNAPSHOT.jar"]
