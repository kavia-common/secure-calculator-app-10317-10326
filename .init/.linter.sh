#!/bin/bash
cd /home/kavia/workspace/code-generation/secure-calculator-app-10317-10326/kotlin_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

