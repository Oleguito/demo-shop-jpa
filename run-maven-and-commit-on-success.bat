@echo off
set JAVA_HOME=C:\JDK17\jdk-17.0.2+8

call docker-compose up -d

call mvnw test

IF %ERRORLEVEL% EQU 0 (
   git add .
   git commit -m "all tests passed %DATE% %TIME%"
)