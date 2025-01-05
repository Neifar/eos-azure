FROM alpine
RUN apk add openjdk17
ADD target/user-0.0.1-RELEASE.jar eos-user-api.jar
CMD ["java","-jar","eos-user-api.jar"]
