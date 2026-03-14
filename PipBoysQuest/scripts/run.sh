#!/usr/bin/env bash
# Utilise bash disponible dans l'environnement courant.
set -euo pipefail
# -e : arrete le script sur la premiere erreur.
# -u : interdit l'usage d'une variable non definie.
# -o pipefail : fait remonter les erreurs d'un pipe.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# Dossier absolu contenant ce script (normalement le dist/ final).
JAR_PATH="$SCRIPT_DIR/app/PipBoysQuest.jar"
# Chemin absolu du .jar distribue avec la release.

if ! command -v java >/dev/null 2>&1; then
  # Verifie qu'un runtime Java est disponible avant le lancement.
  echo "Erreur: Java est introuvable. Installez OpenJDK 17+ puis relancez."
  exit 1
fi

if [[ ! -f "$JAR_PATH" ]]; then
  # Verifie qu'on a bien dezippe tout le dossier dist et pas seulement le script.
  echo "Erreur: fichier manquant '$JAR_PATH'."
  echo "Assurez-vous d'avoir dezippe tout le dossier dist/ complet."
  exit 1
fi

cd "$SCRIPT_DIR"
# Se place dans dist pour que les chemins relatifs du package restent coherents.
java -jar "$JAR_PATH"
# Lance le .jar principal. Le manifeste du build indique deja la classe Main.
