FROM amazoncorretto:11-alpine-jdk
WORKDIR /app
COPY target/studyolle-1.0.0-SNAPSHOT.jar studyolle.jar
EXPOSE 8080
CMD ["java","-jar","studyolle.jar","-Dspring.profiles.active=prod","--spring.config.location=/app/config"]