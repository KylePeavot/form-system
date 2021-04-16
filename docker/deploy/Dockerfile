FROM openjdk:15.0.1-jdk

RUN mkdir /app
ADD . /app
RUN ls /app/target 
RUN cp /app/target/application-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]