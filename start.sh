#!/usr/bin/env sh
set -eu

# Start script for Kavia preview.
# The preview system expects this container to listen on TCP port 3001.
# Configuration is driven by .env (PORT/HOST), with safe defaults.
HOST="${HOST:-0.0.0.0}"
PORT="${PORT:-3001}"

echo "[preview] Starting minimal server on ${HOST}:${PORT} ..."
exec python3 /home/kavia/workspace/code-generation/secure-calculator-app-10317-10326/server.py
