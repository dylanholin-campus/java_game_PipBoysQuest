#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
JAR_PATH="$SCRIPT_DIR/app/PipBoysQuest.jar"

if ! command -v java >/dev/null 2>&1; then
  echo "Erreur: Java est introuvable. Installez OpenJDK 17+ puis relancez."
  exit 1
fi

if [[ ! -f "$JAR_PATH" ]]; then
  echo "Erreur: fichier manquant '$JAR_PATH'."
  echo "Assurez-vous d'avoir dezippe tout le dossier dist/ complet."
  exit 1
fi

cd "$SCRIPT_DIR"
java -jar "$JAR_PATH"
