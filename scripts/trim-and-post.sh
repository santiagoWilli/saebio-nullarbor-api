#!/bin/bash
echo "trim >>>>>> $(date)" >> log.txt

cd "$1"
files=($(ls ./*.fq.gz))

fastqFiles=$(ls ./*.fastq.gz)
for file in $fastqFiles
do
  filename="${${files[1]}%.*}"
  filename="${filename%.*}"
  mv $file $filename.fq.gz
done

trim

files=($(ls ./*_trimmed.fq.gz))
if [ ${#files[@]} == 0 ]
then
  curl -F "status=5" -F "token=$2" $3
  exit 0
fi

file1=${files[0]}
file2=${files[1]}
file1=${file1:2}
file2=${file2:2}

curl -F "status=2" -F "token=$2" -F "file1=@$file1" -F "file2=@$file2" $3

rm -f "${files[0]}" "${files[1]}"