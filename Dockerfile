FROM openjdk:12
WORKDIR /
ADD target/usage-limit-service-0.0.1-SNAPSHOT.jar usage-limit-service-0.0.1-SNAPSHOT.jar
RUN mkdir -p /root/.ssh
CMD java -jar -Dspring.profiles.active=prod usage-limit-service-0.0.1-SNAPSHOT.jar

