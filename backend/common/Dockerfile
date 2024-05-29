FROM maven:3.8.4 as builder

COPY base/pom.xml /app/common/base/

COPY commons/src /app/common/commons/src/
COPY commons/pom.xml /app/common/commons/

RUN mvn -ntp -B -f /app/common/base/pom.xml clean install

FROM amazoncorretto:17

COPY --from=builder /app/common/base/pom.xml /app/
COPY --from=builder /app/common/commons/target/*.jar /app/



