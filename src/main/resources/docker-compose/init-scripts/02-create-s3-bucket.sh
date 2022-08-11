#!/bin/bash
echo "########### Setting S3 as env variables ###########"
export BUCKET_NAME_FOR_CSV=reports-in-csv
export BUCKET_NAME_FOR_JSON=reports-in-json
export BUCKET_NAME_FOR_HTML=reports-in-html

echo "########### Create S3 bucket for CSV reports ###########"
aws --endpoint-url=http://localhost:4566 s3api create-bucket\
    --bucket $BUCKET_NAME_FOR_CSV

echo "########### Create S3 bucket for JSON reports ###########"
aws --endpoint-url=http://localhost:4566 s3api create-bucket\
    --bucket $BUCKET_NAME_FOR_JSON

echo "########### Create S3 bucket for HTML reports ###########"
aws --endpoint-url=http://localhost:4566 s3api create-bucket\
    --bucket $BUCKET_NAME_FOR_HTML

echo "########### List S3 bucket ###########"
aws --endpoint-url=http://localhost:4566 s3api list-buckets

