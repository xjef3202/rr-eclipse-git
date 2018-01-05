FROM java:8

WORKDIR /usr/src/orchService

COPY gradle/wrapper ./gradle/wrapper/
COPY gradlew ./
COPY build.gradle ./

COPY schemas ./schemas/
COPY libs ./libs/
COPY src ./src/

EXPOSE 8080

ENTRYPOINT ["./gradlew"]
CMD [ "bootRun" ]