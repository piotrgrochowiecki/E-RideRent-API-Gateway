FROM openjdk:17-jdk-alpine@sha256:a996cdcc040704ec6badaf5fecf1e144c096e00231a29188596c784bcf858d05
RUN apk update && apk upgrade && apk add bash
EXPOSE 8079
WORKDIR /home/ERideRent_API_Gateway
ADD target/E-RideRent-API-Gateway.jar /home/ERideRent_API_Gateway/
CMD java -jar /home/ERideRent_API_Gateway/E-RideRent-API-Gateway.jar
