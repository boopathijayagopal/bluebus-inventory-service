FROM ubuntu:latest

ENV JDK_VERSION 17
ENV JDK_HOME /usr/lib/jvm/java-${JDK_VERSION}-openjdk-amd64

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    apt-get clean;

ENV JAVA_HOME ${JDK_HOME}

ENV PATH $JAVA_HOME/bin:$PATH

RUN java -version

COPY target/inventoryservice-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085:8085

RUN apt-get update
RUN apt-get install -y gcc
RUN apt-get install -y curl

ENTRYPOINT ["java","-jar","app.jar"]