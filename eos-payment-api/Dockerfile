FROM alpine
RUN apk add openjdk17
ADD target/payment-0.0.1-RELEASE.jar eos-payment-api.jar
CMD ["java","-jar","eos-payment-api.jar"]
