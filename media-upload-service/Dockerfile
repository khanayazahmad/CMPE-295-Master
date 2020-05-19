FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8806
ENTRYPOINT ["java","-Xmx2048m", "-Xms2048m","-jar","/app.jar"]