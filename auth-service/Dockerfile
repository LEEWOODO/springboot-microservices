# JDK 17 이미지 사용
FROM eclipse-temurin:17-jdk

#EXPOSE 8080

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY target/*.jar app.jar

# JAR 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
