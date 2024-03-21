#!/bin/sh

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
set -e
docker build -t amazonlinux-graalvm:latest "$SCRIPT_DIR"
