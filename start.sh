#!/usr/bin/env sh
set -eu

# Start script for Kavia preview.
# The preview system expects this container to listen on TCP port 3001.
# Configuration is driven by environment variables (PORT/HOST), with safe defaults.

# Resolve this script's directory so we can reliably locate server.py regardless of
# where the repo is mounted in the container.
SCRIPT_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"

HOST="${HOST:-0.0.0.0}"
PORT="${PORT:-3001}"

# Ensure server.py sees HOST/PORT (server.py reads these from its own environment).
export HOST
export PORT

echo "[preview] Starting minimal server on ${HOST}:${PORT} ..."
exec python3 "${SCRIPT_DIR}/server.py"
