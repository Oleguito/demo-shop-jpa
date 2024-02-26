@echo off
echo FUCK YOU
set JAVA_HOME=C:\JDK17\jdk-17.0.2+8

call mvnw test

IF %ERRORLEVEL% EQU 0 (
   git add .
   git commit -m "all tests passed %DATE% %TIME%"
)