# Dùng Tomcat
FROM tomcat:10-jdk17

# Xóa app mặc định
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file war vào
COPY target/DAMH_OOPD.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
