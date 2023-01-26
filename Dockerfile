ARG BUILD_HOME=/home/maven/src

FROM maven:3.8.6-ibm-semeru-11-focal AS build
ARG BUILD_HOME
COPY --chown=maven:maven . $BUILD_HOME
WORKDIR $BUILD_HOME
RUN mvn clean install

FROM amazoncorretto:11-alpine3.17-jdk
RUN mkdir /app
COPY --from=build /home/maven/src/target/*.jar /app/bloomberg.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/app/bloomberg.jar"]