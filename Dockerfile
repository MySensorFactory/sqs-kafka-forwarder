FROM eclipse-temurin:21-jdk-jammy AS build

COPY target/sqs-kafka-forwarder.jar /home/service.jar
WORKDIR /home/
CMD java -jar service.jar