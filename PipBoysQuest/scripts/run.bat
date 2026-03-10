@echo off
setlocal

set "SCRIPT_DIR=%~dp0"
for %%I in ("%SCRIPT_DIR%") do set "SCRIPT_DIR=%%~fI"

cd /d "%SCRIPT_DIR%"
java -jar app\PipBoysQuest.jar
