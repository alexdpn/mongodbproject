FROM java:8
COPY target/mongodb-project-1.0-SNAPSHOT.jar /home/app/
ADD target/lib /home/app/lib
WORKDIR /home/app
CMD ["java", "-jar", "mongodb-project-1.0-SNAPSHOT.jar"]
