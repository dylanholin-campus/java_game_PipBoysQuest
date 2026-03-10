@echo off
setlocal enabledelayedexpansion

set "SCRIPT_DIR=%~dp0"
set "PROJECT_DIR=%SCRIPT_DIR%.."
for %%I in ("%PROJECT_DIR%") do set "PROJECT_DIR=%%~fI"
set "ROOT_DIR=%PROJECT_DIR%\.."
for %%I in ("%ROOT_DIR%") do set "ROOT_DIR=%%~fI"

set "BUILD_DIR=%PROJECT_DIR%\build"
set "DIST_DIR=%PROJECT_DIR%\dist"
set "CLASSES_DIR=%BUILD_DIR%\classes"
set "JAR_PATH=%DIST_DIR%\app\PipBoysQuest.jar"
set "MANIFEST_PATH=%BUILD_DIR%\manifest.mf"

if exist "%BUILD_DIR%" rmdir /s /q "%BUILD_DIR%"
if exist "%DIST_DIR%" rmdir /s /q "%DIST_DIR%"

mkdir "%CLASSES_DIR%"
mkdir "%DIST_DIR%\app"
mkdir "%DIST_DIR%\lib"
mkdir "%DIST_DIR%\image"

pushd "%PROJECT_DIR%"
javac -d "%CLASSES_DIR%" -cp "%ROOT_DIR%\lib\mysql-connector.jar" @sources.txt
if errorlevel 1 exit /b 1
popd

(
  echo Manifest-Version: 1.0
  echo Main-Class: serenadebird.pipboysquest.main.Main
  echo Class-Path: ../lib/mysql-connector.jar
) > "%MANIFEST_PATH%"

jar cfm "%JAR_PATH%" "%MANIFEST_PATH%" -C "%CLASSES_DIR%" .
if errorlevel 1 exit /b 1

copy /y "%ROOT_DIR%\lib\mysql-connector.jar" "%DIST_DIR%\lib\" >nul
copy /y "%ROOT_DIR%\image\main_screen_game.jpg" "%DIST_DIR%\image\" >nul
copy /y "%ROOT_DIR%\image\dead.gif" "%DIST_DIR%\image\" >nul
copy /y "%SCRIPT_DIR%run.bat" "%DIST_DIR%\run.bat" >nul
copy /y "%SCRIPT_DIR%run.sh" "%DIST_DIR%\run.sh" >nul

echo Build termine: %JAR_PATH%
echo Lancement: cd /d "%DIST_DIR%" ^&^& run.bat
