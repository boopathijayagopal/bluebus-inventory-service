FROM eclipse-temurin:20-jdk-alpine

WORKDIR /app

COPY target/inventoryservice-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085:8085

ENTRYPOINT ["java","-jar","app.jar"]