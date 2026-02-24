#!/usr/bin/env python3
"""
Minimal HTTP server used for preview readiness.

The Kavia preview system for `sh` containers expects the container to bind/listen on TCP
port 3001 (or the port specified in the PORT environment variable). This repository
is primarily an Android/Kotlin sample scaffold and does not otherwise provide a
backend server, so we expose a tiny server solely to satisfy preview readiness.

Endpoints:
- GET /health -> 200 OK {"status":"ok"}
- GET /       -> 200 OK plain text description
"""

from __future__ import annotations

import json
import os
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from typing import Any


class Handler(BaseHTTPRequestHandler):
    """HTTP handler for readiness and basic info responses."""

    server_version = "secure-calculator-preview/1.0"

    def _send_json(self, status_code: int, payload: dict[str, Any]) -> None:
        body = json.dumps(payload).encode("utf-8")
        self.send_response(status_code)
        self.send_header("Content-Type", "application/json; charset=utf-8")
        self.send_header("Content-Length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)

    def _send_text(self, status_code: int, text: str) -> None:
        body = text.encode("utf-8")
        self.send_response(status_code)
        self.send_header("Content-Type", "text/plain; charset=utf-8")
        self.send_header("Content-Length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)

    def do_GET(self) -> None:  # noqa: N802 (BaseHTTPRequestHandler naming)
        if self.path in ("/health", "/healthz", "/ready", "/readyz"):
            self._send_json(200, {"status": "ok"})
            return

        if self.path == "/" or self.path.startswith("/?"):
            self._send_text(
                200,
                "secure-calculator-app preview server is running.\n"
                "Use GET /health for readiness.\n",
            )
            return

        self._send_json(404, {"error": "not_found", "path": self.path})

    def log_message(self, format: str, *args: object) -> None:
        # Keep logs concise for preview environment.
        return


def main() -> None:
    host = os.environ.get("HOST", "0.0.0.0")
    port_str = os.environ.get("PORT", "3001")

    try:
        port = int(port_str)
    except ValueError:
        raise SystemExit(f"Invalid PORT value {port_str!r}; expected an integer.")

    httpd = ThreadingHTTPServer((host, port), Handler)
    print(f"[preview] Listening on http://{host}:{port} (health: /health)")
    httpd.serve_forever()


if __name__ == "__main__":
    main()
