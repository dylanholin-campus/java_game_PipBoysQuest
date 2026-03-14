@echo off
rem Desactive l'affichage brut des commandes pour ne garder que les messages utiles.
setlocal
rem Ouvre une portee locale pour ne pas polluer les variables d'environnement du terminal.

set "SCRIPT_DIR=%~dp0"
rem %~dp0 = dossier qui contient ce script (normalement le dist/ final).
for %%I in ("%SCRIPT_DIR%") do set "SCRIPT_DIR=%%~fI"
rem Normalise le chemin vers une version absolue exploitable partout.
set "JAVA_CMD=java"
rem Par defaut, on essaie d'utiliser java depuis le PATH systeme.

if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" set "JAVA_CMD=%JAVA_HOME%\bin\java.exe"
rem Si JAVA_HOME pointe vers un JDK/JRE valide, on l'utilise en priorite.

if "%JAVA_CMD%"=="java" if exist "%USERPROFILE%\.jdks" (
  rem Fallback pratique pour les JDK telecharges par IntelliJ / JetBrains.
  for /f "delims=" %%I in ('dir /b /ad /o-n "%USERPROFILE%\.jdks"') do (
	if exist "%USERPROFILE%\.jdks\%%I\bin\java.exe" (
	  set "JAVA_CMD=%USERPROFILE%\.jdks\%%I\bin\java.exe"
	  rem Des qu'un binaire java est trouve, on arrete la recherche.
	  goto :java_ready
	)
  )
)

if "%JAVA_CMD%"=="java" if exist "%ProgramFiles%\Java" (
  rem Dernier fallback classique: installation Java sous Program Files.
  for /f "delims=" %%I in ('dir /b /ad /o-n "%ProgramFiles%\Java"') do (
	if exist "%ProgramFiles%\Java\%%I\bin\java.exe" (
	  set "JAVA_CMD=%ProgramFiles%\Java\%%I\bin\java.exe"
	  rem On a trouve un runtime exploitable, on passe a la validation.
	  goto :java_ready
	)
  )
)

:java_ready
rem Toutes les strategies de detection convergent ici.

if "%JAVA_CMD%"=="java" (
  rem Si on utilise le PATH, on verifie que java y est bien visible.
  where java >nul 2>nul
  if errorlevel 1 (
	echo Erreur: java introuvable. Configurez JAVA_HOME ou installez Java 17+.
	exit /b 1
  )
) else (
  rem Si JAVA_CMD est un chemin absolu, on verifie que le fichier existe reellement.
  if not exist "%JAVA_CMD%" (
	echo Erreur: java introuvable. Configurez JAVA_HOME ou installez Java 17+.
	exit /b 1
  )
)

cd /d "%SCRIPT_DIR%"
rem /d autorise aussi le changement de lecteur (ex: C: -> D:).
rem On se place dans dist pour que le chemin relatif app\PipBoysQuest.jar fonctionne.
"%JAVA_CMD%" -jar app\PipBoysQuest.jar
rem Lance le .jar principal de la release. Le manifeste gere la classe Main.
