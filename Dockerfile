# Use a base image with Java
FROM eclipse-temurin:17-jdk-alpine

# Copy the built jar file into the image
COPY build/libs/PaymentHub-0.0.1-SNAPSHOT.jar PaymentHub-0.0.1-SNAPSHOT.jar

# Set the entry point to run your application
ENTRYPOINT ["java","-jar","/PaymentHub-0.0.1-SNAPSHOT.jar"]
