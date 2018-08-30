import boto3

client=boto3.client('s3')
client.download_file('okaram-simple-s3-bucket2', 'stuff.txt', 'stuff1.txt')
