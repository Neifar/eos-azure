FROM alpine
RUN apk add openjdk17
ADD target/order-0.0.1-RELEASE.jar eos-order-api.jar
CMD ["java","-jar","eos-order-api.jar"]
