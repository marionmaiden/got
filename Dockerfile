FROM maven:3.6.3-adoptopenjdk-14

COPY ./pom.xml ./pom.xml
COPY ./application ./application
COPY ./domain ./domain
COPY ./infrastructure ./infrastructure
COPY ./presentation ./presentation

RUN mvn install
RUN cp ./application/target/application.jar ../application.jar

CMD ["java", "-jar", "./application.jar"]