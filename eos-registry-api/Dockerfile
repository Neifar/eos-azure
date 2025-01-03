FROM alpine
RUN apk add openjdk11
COPY target/registry-0.0.1-RELEASE.jar eos-registry-api.jar
CMD ["java","-jar","eos-registry-api.jar"]
