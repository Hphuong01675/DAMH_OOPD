# Stage 1: Build project với Maven
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copy pom.xml và tải dependencies trước để tận dụng cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy toàn bộ code và build file war
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Chạy với Tomcat
FROM tomcat:10-jdk17
WORKDIR /usr/local/tomcat

# Xóa các app mặc định của Tomcat
RUN rm -rf webapps/*

# Copy file war từ Stage 1 sang
COPY --from=build /app/target/DrinkStoreManagement.war webapps/ROOT.war

# Khai báo các biến môi trường
ENV DB_URL=""
ENV DB_USER=""
ENV DB_PASSWORD=""
ENV CLOUDINARY_NAME=""
ENV CLOUDINARY_API_KEY=""
ENV CLOUDINARY_API_SECRET=""

EXPOSE 8080
CMD ["catalina.sh", "run"]
