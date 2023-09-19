# Meant to be used to copy the local build of the project into a container. Should be faster for testing purposes.
# To build the image, run the following from the root of the project:
# $ ./gradlew umaserverdemo:buildFatJar
# $ docker build -f umaserverdemo/localbuild.Dockerfile -t umaserverdemo .
# then run:
# $ docker run -it --rm -p 8080:8080 umaserverdemo
FROM openjdk:17-buster

COPY ./umaserverdemo/build/libs/*.jar /app/umaserverdemo.jar

ENTRYPOINT ["java","-jar","/app/umaserverdemo.jar"]