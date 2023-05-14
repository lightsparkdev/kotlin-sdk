./gradlew dokkaHtmlMultiModule
aws s3 sync build/dokka/htmlMultiModule s3://lightspark-dev-web/docs/reference/kotlin --delete