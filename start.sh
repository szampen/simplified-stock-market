#!/bin/bash
# Usage: ./start.sh 8080

export EXTERNAL_PORT=${1:-8080}
echo "Starting Stock Market System on port: $EXTERNAL_PORT"
docker compose up --build -d