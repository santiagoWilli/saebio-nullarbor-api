#!/bin/bash
echo "run >>>>>> $(date)" >> log.txt

cd "$1"

reference=$(ls *.fa)
if ! [ -f "$reference" ]; then
    reference=$(ls *.gbf)
fi
generatecsv
run "$reference"

resultFolder=$(echo ./result-*)
resultFolder=${resultFolder:2}
reportFile=$resultFolder/report/index.html
if [ -f "$reportFile" ]; then
    curl -F "status=2" -F "token=$2" -F "file=@$reportFile" $3
else
    curl -F "status=5" -F "token=$2" $3
fi

cd ../
rm -rf "$2"