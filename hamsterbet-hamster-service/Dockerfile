FROM maven:3-eclipse-temurin-21

RUN mkdir -p /app && \
    groupadd --system app && \
    useradd --system --shell /bin/false --gid app app

COPY --chown=app:app ./target/hamsterbet-hamster-service-*.jar /app/app.jar

VOLUME /tmp

WORKDIR /app

USER app

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "/app/app.jar"]