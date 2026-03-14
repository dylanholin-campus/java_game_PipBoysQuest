@echo off
rem Desactive l'echo automatique des commandes pour garder une sortie lisible.
setlocal enabledelayedexpansion
rem Ouvre une portee locale pour les variables du script.
rem Le mode delayed expansion est actif par securite si le script evolue.

set "SCRIPT_DIR=%~dp0"
rem %~dp0 = dossier du script courant (ici ...\PipBoysQuest\scripts\).
set "PROJECT_DIR=%SCRIPT_DIR%.."
rem Remonte d'un niveau pour viser le dossier projet PipBoysQuest.
for %%I in ("%PROJECT_DIR%") do set "PROJECT_DIR=%%~fI"
rem Normalise PROJECT_DIR en chemin absolu propre.
set "ROOT_DIR=%PROJECT_DIR%\.."
rem ROOT_DIR pointe vers la racine du repo (au-dessus de PipBoysQuest).
for %%I in ("%ROOT_DIR%") do set "ROOT_DIR=%%~fI"
rem Normalise aussi ROOT_DIR en chemin absolu.

set "BUILD_DIR=%PROJECT_DIR%\build"
rem Dossier temporaire de compilation.
set "DIST_DIR=%PROJECT_DIR%\dist"
rem Dossier final distribuable (zip release).
set "CLASSES_DIR=%BUILD_DIR%\classes"
rem Classes .class generees par javac.
set "JAR_PATH=%DIST_DIR%\app\PipBoysQuest.jar"
rem Emplacement du .jar final.
set "MANIFEST_PATH=%BUILD_DIR%\manifest.mf"
rem Fichier manifeste Java insere dans le .jar.
set "MYSQL_JAR=%ROOT_DIR%\lib\mysql-connector.jar"
rem Dependance MySQL optionnelle. Si absente, le jeu fonctionne hors-ligne.
set "JAVA_CMD=java"
rem Valeur par defaut: on utilisera java trouve dans le PATH.
set "JAVAC_CMD=javac"
rem Valeur par defaut: on utilisera javac trouve dans le PATH.

if defined JAVA_HOME (
  rem Si JAVA_HOME est defini, on privilegie ce JDK.
  if exist "%JAVA_HOME%\bin\javac.exe" set "JAVAC_CMD=%JAVA_HOME%\bin\javac.exe"
  if exist "%JAVA_HOME%\bin\java.exe" set "JAVA_CMD=%JAVA_HOME%\bin\java.exe"
)

if "%JAVAC_CMD%"=="javac" if exist "%USERPROFILE%\.jdks" (
  rem Si JAVA_HOME n'aide pas, on cherche un JDK local JetBrains dans ~/.jdks.
  for /f "delims=" %%I in ('dir /b /ad /o-n "%USERPROFILE%\.jdks"') do (
    rem /o-n = tri decroissant pour prendre un JDK recent en premier.
    if exist "%USERPROFILE%\.jdks\%%I\bin\javac.exe" (
      set "JAVAC_CMD=%USERPROFILE%\.jdks\%%I\bin\javac.exe"
      set "JAVA_CMD=%USERPROFILE%\.jdks\%%I\bin\java.exe"
      rem On a trouve un JDK valide: inutile de continuer la recherche.
      goto :java_ready
    )
  )
)

if "%JAVAC_CMD%"=="javac" if exist "%ProgramFiles%\Java" (
  rem Dernier fallback Windows classique: les JDK installes dans Program Files\Java.
  for /f "delims=" %%I in ('dir /b /ad /o-n "%ProgramFiles%\Java"') do (
    if exist "%ProgramFiles%\Java\%%I\bin\javac.exe" (
      set "JAVAC_CMD=%ProgramFiles%\Java\%%I\bin\javac.exe"
      if exist "%ProgramFiles%\Java\%%I\bin\java.exe" set "JAVA_CMD=%ProgramFiles%\Java\%%I\bin\java.exe"
      rem Des qu'un JDK est trouve, on saute a la phase de validation.
      goto :java_ready
    )
  )
)

:java_ready
rem Toutes les strategies de detection convergent ici.

if "%JAVAC_CMD%"=="javac" (
  rem Cas 1: on compte sur le PATH. On verifie donc que javac y est bien present.
  where javac >nul 2>nul
  if errorlevel 1 (
    echo Erreur: javac introuvable. Configurez JAVA_HOME ou installez un JDK 17+.
    exit /b 1
  )
) else (
  rem Cas 2: on utilise un chemin absolu vers javac. On verifie que le fichier existe.
  if not exist "%JAVAC_CMD%" (
    echo Erreur: javac introuvable. Configurez JAVA_HOME ou installez un JDK 17+.
    exit /b 1
  )
)

if exist "%BUILD_DIR%" rmdir /s /q "%BUILD_DIR%"
rem Nettoie le precedent dossier de build pour repartir d'un etat propre.
if exist "%DIST_DIR%" rmdir /s /q "%DIST_DIR%"
rem Nettoie aussi le precedent dossier dist afin d'eviter des fichiers obsoletes.

mkdir "%CLASSES_DIR%"
rem Cree build/classes meme si build n'existe pas encore: mkdir cree l'arborescence utile.
mkdir "%DIST_DIR%\app"
rem Sous-dossier qui recevra le .jar final.
mkdir "%DIST_DIR%\lib"
rem Sous-dossier reserve aux librairies optionnelles distribuees avec le jeu.
mkdir "%DIST_DIR%\image"
rem Sous-dossier des assets visuels utilises par GameWindow.

pushd "%PROJECT_DIR%"
rem Se place dans le dossier projet pour que @sources.txt soit resolu correctement.
if exist "%MYSQL_JAR%" (
  rem Si le driver MySQL existe, on l'ajoute au classpath de compilation.
  "%JAVAC_CMD%" -d "%CLASSES_DIR%" -cp "%MYSQL_JAR%" @sources.txt
) else (
  echo Avertissement: mysql-connector.jar absent. Build en mode hors-ligne.
  rem Sans connecteur, la compilation reste possible car le jeu degrade en mode hors-ligne.
  "%JAVAC_CMD%" -d "%CLASSES_DIR%" @sources.txt
)
if errorlevel 1 exit /b 1
rem Stoppe immediatement le script si la compilation echoue.
popd
rem Revient au dossier initial du terminal.

(
  rem Ecrit le manifeste du .jar ligne par ligne.
  echo Manifest-Version: 1.0
  rem Version standard du format de manifeste Java.
  echo Main-Class: serenadebird.pipboysquest.main.Main
  rem Classe principale executee lors de java -jar.
  if exist "%MYSQL_JAR%" echo Class-Path: ../lib/mysql-connector.jar
  rem Si le driver est distribue, on le declare dans le manifeste pour le .jar final.
) > "%MANIFEST_PATH%"

jar cfm "%JAR_PATH%" "%MANIFEST_PATH%" -C "%CLASSES_DIR%" .
rem c = create, f = sortie vers un fichier, m = manifeste fourni.
rem -C "%CLASSES_DIR%" . = empaquette le contenu compile depuis build/classes.
if errorlevel 1 exit /b 1
rem Stoppe si la generation du .jar echoue.

if exist "%MYSQL_JAR%" copy /y "%MYSQL_JAR%" "%DIST_DIR%\lib\" >nul
rem Copie le connecteur dans dist/lib pour l'execution java -jar quand la BDD est utilisee.
copy /y "%ROOT_DIR%\image\main_screen_game.jpg" "%DIST_DIR%\image\" >nul
rem Copie l'image de fond principale du Pip-Boy dans le package dist.
copy /y "%ROOT_DIR%\image\dead.gif" "%DIST_DIR%\image\" >nul
rem Copie le gif de defaite utilise par l'interface.
copy /y "%SCRIPT_DIR%run.bat" "%DIST_DIR%\run.bat" >nul
rem Duplique le script Windows dans dist pour qu'un joueur puisse lancer la release.
copy /y "%SCRIPT_DIR%run.sh" "%DIST_DIR%\run.sh" >nul
rem Duplique aussi le script Unix/macOS dans dist.

echo Build termine: %JAR_PATH%
rem Affiche l'emplacement exact du .jar genere.
echo Lancement: cd /d "%DIST_DIR%" ^&^& run.bat
rem Donne au developpeur la commande de lancement immediate sous Windows.
