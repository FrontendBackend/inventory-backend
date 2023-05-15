FROM openjdk:11-jdk
COPY target/back.jar back.jar
# CMD ["mysqld"]

EXPOSE 1999


# ENTRYPOINT ["java","-jar","/back.jar", "/path/to/start.sh"]
ENTRYPOINT ["java","-jar","/back.jar"]


