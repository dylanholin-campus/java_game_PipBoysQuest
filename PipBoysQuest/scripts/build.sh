#!/usr/bin/env bash
# Utilise bash trouve dans l'environnement courant.
set -euo pipefail
# -e : arrete le script a la premiere commande en erreur.
# -u : echoue si une variable non definie est utilisee.
# -o pipefail : propage l'erreur d'une commande dans un pipe.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# Dossier absolu du script build.sh.
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
# Dossier du projet PipBoysQuest.
ROOT_DIR="$(cd "$PROJECT_DIR/.." && pwd)"
# Racine du repo, utile pour les images et la lib optionnelle.
BUILD_DIR="$PROJECT_DIR/build"
# Dossier temporaire de compilation.
DIST_DIR="$PROJECT_DIR/dist"
# Dossier final distribuable.
CLASSES_DIR="$BUILD_DIR/classes"
# Sortie de javac.
JAR_PATH="$DIST_DIR/app/PipBoysQuest.jar"
# Emplacement final du .jar.
MANIFEST_PATH="$BUILD_DIR/manifest.mf"
# Manifeste Java injecte dans le .jar.
MYSQL_JAR="$ROOT_DIR/lib/mysql-connector.jar"
# Driver MySQL optionnel. Le jeu reste jouable sans lui.

if ! command -v javac >/dev/null 2>&1; then
  echo "Erreur: javac est introuvable. Installez un JDK 17+ puis relancez."
  exit 1
fi
# Verifie qu'un compilateur Java est disponible avant d'aller plus loin.

if ! command -v jar >/dev/null 2>&1; then
  echo "Erreur: la commande jar est introuvable. Verifiez votre installation JDK."
  exit 1
fi
# Verifie aussi l'outil d'empaquetage du JDK.

rm -rf "$BUILD_DIR" "$DIST_DIR"
# Supprime les anciens repertoires de build/dist pour repartir d'un etat propre.
mkdir -p "$CLASSES_DIR" "$DIST_DIR/app" "$DIST_DIR/lib" "$DIST_DIR/image"
# Recree l'arborescence attendue pour compiler puis preparer la release.

pushd "$PROJECT_DIR" >/dev/null
# Se place dans le dossier projet pour resoudre correctement @sources.txt.
if [[ -f "$MYSQL_JAR" ]]; then
  # Si le driver MySQL existe, on l'ajoute au classpath de compilation.
  javac -d "$CLASSES_DIR" -cp "$MYSQL_JAR" @sources.txt
else
  echo "Avertissement: mysql-connector.jar absent. Build en mode hors-ligne."
  # Sans le connecteur, le jeu compile quand meme et degrade en mode hors-ligne.
  javac -d "$CLASSES_DIR" @sources.txt
fi
popd >/dev/null
# Revient silencieusement au dossier precedent.

{
  # Ecrit le manifeste du .jar ligne par ligne.
  echo "Manifest-Version: 1.0"
  # Version standard du format manifeste.
  echo "Main-Class: serenadebird.pipboysquest.main.Main"
  # Classe principale executee lors de java -jar.
  if [[ -f "$MYSQL_JAR" ]]; then
	echo "Class-Path: ../lib/mysql-connector.jar"
    # Si le driver est fourni dans dist/lib, on le declare dans le manifeste.
  fi
} > "$MANIFEST_PATH"

jar cfm "$JAR_PATH" "$MANIFEST_PATH" -C "$CLASSES_DIR" .
# c = create, f = sortie vers fichier, m = manifeste fourni.
# -C "$CLASSES_DIR" . = empaquette les .class compiles depuis ce dossier.

if [[ -f "$MYSQL_JAR" ]]; then
  cp "$MYSQL_JAR" "$DIST_DIR/lib/"
  # Copie la dependance MySQL dans le package dist seulement si elle existe.
fi
cp "$ROOT_DIR/image/main_screen_game.jpg" "$DIST_DIR/image/"
# Copie le fond principal de l'interface Swing.
cp "$ROOT_DIR/image/dead.gif" "$DIST_DIR/image/"
# Copie l'animation/gif de defaite.
cp "$SCRIPT_DIR/run.sh" "$DIST_DIR/run.sh"
# Place le script de lancement Unix dans dist.
cp "$SCRIPT_DIR/run.bat" "$DIST_DIR/run.bat"
# Place aussi le script Windows dans dist pour les utilisateurs Windows.

chmod +x "$DIST_DIR/run.sh"
# Rend le script Unix executable dans le package final.

echo "Build termine: $JAR_PATH"
# Indique ou se trouve le .jar genere.
echo "Lancement: cd '$DIST_DIR' && ./run.sh"
# Donne la commande de lancement immediate sous Unix/macOS/Linux.
