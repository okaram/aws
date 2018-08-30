import boto3
import io

output = io.BytesIO()
client=boto3.client('s3')

client.download_fileobj('okaram-simple-s3-bucket2', 'stuff.txt', output)
print(output.getvalue().decode())
