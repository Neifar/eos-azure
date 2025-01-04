FROM alpine
RUN apk add openjdk17
COPY target/gateway-0.0.1-RELEASE.jar eos-gateway-api.jar
CMD ["java","-jar","eos-gateway-api.jar"]
