FROM openjdk:17-jdk-slim
WORKDIR /Final_Project_9team
ADD /build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]