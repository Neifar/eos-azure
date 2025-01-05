FROM alpine
RUN apk add openjdk17
ADD target/kart-0.0.1-RELEASE.jar eos-kart-api.jar
CMD ["java","-jar","eos-kart-api.jar"]
