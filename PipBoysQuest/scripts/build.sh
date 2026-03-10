#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
ROOT_DIR="$(cd "$PROJECT_DIR/.." && pwd)"
BUILD_DIR="$PROJECT_DIR/build"
DIST_DIR="$PROJECT_DIR/dist"
CLASSES_DIR="$BUILD_DIR/classes"
JAR_PATH="$DIST_DIR/app/PipBoysQuest.jar"
MANIFEST_PATH="$BUILD_DIR/manifest.mf"

rm -rf "$BUILD_DIR" "$DIST_DIR"
mkdir -p "$CLASSES_DIR" "$DIST_DIR/app" "$DIST_DIR/lib" "$DIST_DIR/image"

pushd "$PROJECT_DIR" >/dev/null
javac -d "$CLASSES_DIR" -cp "$ROOT_DIR/lib/mysql-connector.jar" @sources.txt
popd >/dev/null

cat > "$MANIFEST_PATH" <<'EOF'
Manifest-Version: 1.0
Main-Class: serenadebird.pipboysquest.main.Main
Class-Path: ../lib/mysql-connector.jar
EOF

jar cfm "$JAR_PATH" "$MANIFEST_PATH" -C "$CLASSES_DIR" .

cp "$ROOT_DIR/lib/mysql-connector.jar" "$DIST_DIR/lib/"
cp "$ROOT_DIR/image/main_screen_game.jpg" "$DIST_DIR/image/"
cp "$ROOT_DIR/image/dead.gif" "$DIST_DIR/image/"
cp "$SCRIPT_DIR/run.sh" "$DIST_DIR/run.sh"
cp "$SCRIPT_DIR/run.bat" "$DIST_DIR/run.bat"

chmod +x "$DIST_DIR/run.sh"

echo "Build termine: $JAR_PATH"
echo "Lancement: cd '$DIST_DIR' && ./run.sh"
