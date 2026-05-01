# BUILDING
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
# COPYING POM AND DOWNLOADING DEPENDENCIES
COPY pom.xml .
RUN mvn dependency:go-offline
# COPYING SOURCE AND BUILDING JAR
COPY src ./src
RUN mvn clean package -DskipTests

# RUNNING
FROM eclipse-temurin:21-jre-alpine
# INSTALLING CURL FOR HEALTHCHECK
RUN apk add --no-cache curl
WORKDIR /app
# COPYING JAR FROM EARLIER PHASE
COPY --from=build /app/target/*.jar app.jar
# STARTING COMMAND
ENTRYPOINT ["java", "-jar", "app.jar"]