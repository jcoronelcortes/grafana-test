FROM amazoncorretto:17.0.3-alpine as corretto-jdk
EXPOSE 8080
ARG JAR=aplicacion-test.jar
COPY target/$JAR /app.jar
COPY opentelemetry-javaagent.jar /opentelemetry-javaagent.jar
ENTRYPOINT ["java","-jar","/app.jar"]