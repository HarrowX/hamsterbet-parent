# Базовый образ с JDK и Maven
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# 1. Копируем только POM-файлы сначала
COPY pom.xml .
COPY hamsterbet-hamster-service/pom.xml hamsterbet-hamster-service/pom.xml
COPY hamsterbet-common/pom.xml hamsterbet-common/pom.xml

# 2. Собираем общие модули
RUN mvn install -pl hamsterbet-common -am

# 3. Копируем исходные коды
COPY hamsterbet-common/src hamsterbet-common/src
COPY hamsterbet-hamster-service/src hamsterbet-hamster-service/src

# 4. Собираем основной сервис
RUN mvn package -pl hamsterbet-hamster-service -am -DskipTests

# Финальный образ
FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S app && adduser -S -G app app
USER app
COPY --from=builder /app/hamsterbet-hamster-service/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]