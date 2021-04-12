#!/bin/bash

cd scripts || exit 1
echo "start $(date)" >> log.txt
echo "$@" >> log.txt
nohup "$@" &