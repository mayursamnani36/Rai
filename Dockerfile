FROM openjdk:20-oracle
COPY target/*.jar rai.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "rai.jar"]