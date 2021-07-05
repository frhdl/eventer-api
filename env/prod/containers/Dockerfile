FROM adoptopenjdk/openjdk11:jdk-11.0.9.1_1
VOLUME /tmp
COPY build/libs/eventer-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]