# secure-calculator-app-10317-10326

This repository is primarily an Android/Kotlin sample scaffold (`kotlin_frontend/`).

## Preview/Container startup (port 3001)

The Kavia preview system expects the `sh` container to bind/listen on TCP port **3001**.
Since this repo does not include a backend server, a minimal readiness server is provided.

Run:

```sh
./start.sh
```

Then verify:

```sh
curl -s http://localhost:3001/health
```