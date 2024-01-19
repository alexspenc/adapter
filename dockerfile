FROM amazoncorretto:17
WORKDIR /app
COPY ./target/adapter-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]