ARG BASE_NAME=base

FROM ${BASE_NAME} AS base
FROM maven:3.8.4 AS builder

COPY --from=base /app/ /app/lib/
COPY src /app/src
COPY pom.xml /app

RUN mvn install:install-file -Dfile=/app/lib/pom.xml -DgroupId=com.vladyslavberezovskyi -DartifactId=base -Dversion=0.0.1-SNAPSHOT -Dpackaging=pom
RUN mvn install:install-file -Dfile=/app/lib/commons-0.0.1-SNAPSHOT.jar -DgroupId=com.vladyslavberezovskyi -DartifactId=commons -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar

RUN mvn -f /app/pom.xml clean package -DskipTests

FROM amazoncorretto:17

WORKDIR /app

COPY --from=builder /app/target/*.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]
