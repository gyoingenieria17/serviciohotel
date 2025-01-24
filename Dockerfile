FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/serviciohotel-0.0.1.jar
COPY ${JAR_FILE} app_serviciohotel.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app_serviciohotel.jar"]