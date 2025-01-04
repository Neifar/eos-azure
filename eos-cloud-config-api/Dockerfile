FROM alpine
RUN apk add openjdk17
COPY target/server-0.0.1-RELEASE.jar eos-cloud-config-api.jar
CMD ["java","-jar","eos-cloud-config-api.jar"]
