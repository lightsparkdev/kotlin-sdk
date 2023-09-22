# Meant to be used to copy the local build of the project into a container.
# Should be faster for testing purposes.
# To build the image, run the following from the root of the project:
# $ ./gradlew remotesignerdemo:buildFatJar
# $ docker build -f remotesignerdemo/localbuild.Dockerfile -t remotesignerdemo .
# then run:
# $ docker run -it --rm -p 8080:8080 remotesignerdemo
FROM openjdk:17-buster

COPY ./remotesignerdemo/build/libs/*.jar /app/remotesignerdemo.jar

ENTRYPOINT ["java","-jar","/app/remotesignerdemo.jar"]