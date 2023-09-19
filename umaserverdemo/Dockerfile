FROM --platform=linux/amd64 cimg/android:2023.09.1-ndk as build

COPY . /home/gradle/src

USER root

WORKDIR /home/gradle/src

RUN gradle umaserverdemo:buildFatJar --no-daemon

FROM openjdk:17-buster

COPY --from=build /home/gradle/src/umaserverdemo/build/libs/*.jar /app/umaserverdemo.jar

ENTRYPOINT ["java","-jar","/app/umaserverdemo.jar"]