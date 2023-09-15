FROM cimg/android:2023.09.1-ndk as build

COPY . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle build umaserverdemo:jar

FROM amazoncorretto:17.0.8-alpine3.18

COPY --from=build /home/gradle/src/build/libs/*.jar /app/umaserverdemo.jar

WORKDIR /app

CMD ["java", "-jar", "umaserverdemo.jar"]