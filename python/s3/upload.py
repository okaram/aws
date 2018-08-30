import boto3

client=boto3.client('s3')
client.upload_file( 'stuff.txt','okaram-simple-s3-bucket2', 'stuff.txt')