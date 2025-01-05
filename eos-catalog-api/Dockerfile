FROM alpine
RUN apk add openjdk17
ADD target/catalog-0.0.1-RELEASE.jar eos-catalog-api.jar
CMD ["java","-jar","eos-catalog-api.jar"]
