@echo off
title Blood Donation System Cluster Manager
echo ==========================================================
echo Starting Blood Donation Management System Cluster...
echo Session Database replication: Enabled (MySQL)
echo ==========================================================
echo.

echo Launching Instance 1 on Port 8080...
start "Spring Boot - Port 8080" cmd /k ".\mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8080"
echo Waiting for port 8080 to bind...
ping 127.0.0.1 -n 11 >nul

echo.
echo Launching Instance 2 on Port 8081...
start "Spring Boot - Port 8081" cmd /k ".\mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081"
echo Waiting for port 8081 to bind...
ping 127.0.0.1 -n 6 >nul

echo.
echo Launching Instance 3 on Port 8082...
start "Spring Boot - Port 8082" cmd /k ".\mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8082"

echo.
echo ==========================================================
echo All 3 instances are starting up in separate terminal windows!
echo - Instance 1: http://localhost:8080
echo - Instance 2: http://localhost:8081
echo - Instance 3: http://localhost:8082
echo.
echo Launch Nginx using "nginx -c %cd%\nginx.conf" to balance on:
echo - Load Balanced Gate: http://localhost
echo ==========================================================
ping 127.0.0.1 -n 4 >nul
