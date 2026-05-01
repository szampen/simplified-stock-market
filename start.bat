@echo off
set EXTERNAL_PORT=%1
if "%EXTERNAL_PORT%"=="" set EXTERNAL_PORT=8080
docker-compose up --build -d