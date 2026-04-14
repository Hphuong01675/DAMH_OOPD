# Dùng Tomcat
FROM tomcat:10-jdk17

# Xóa app mặc định
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file war vào
COPY target/DAMH_OOPD.war /usr/local/tomcat/webapps/ROOT.war

# Khai báo các biến môi trường cho PostgreSQL
ENV DB_URL=""
ENV DB_USER=""
ENV DB_PASSWORD=""

# Khai báo các biến môi trường cho Cloudinary
ENV CLOUDINARY_NAME=""
ENV CLOUDINARY_API_KEY=""
ENV CLOUDINARY_API_SECRET=""

EXPOSE 8080
