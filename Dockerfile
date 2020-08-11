FROM openjdk:11.0.8-jdk
COPY ./Service.java /tmp

EXPOSE 8080/tcp

ENTRYPOINT ["/usr/local/openjdk-11/bin/java", "-ea", "--source", "11", "/tmp/Service.java"]
