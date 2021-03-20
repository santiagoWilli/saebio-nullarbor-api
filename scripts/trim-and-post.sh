#!/bin/bash

cd "$1" || exit 1
trim

files=($(ls ./*_trimmed.fq.gz))
if [ ${#files[@]} == 0 ]
then
  curl -F "status=5" -F "token=$2" $3
  exit 0
fi

curl -F "status=2" -F "token=$2" -F "file1=@${files[0]}" -F "file2=@${files[1]}" $3
rm -f $files