@Grab('com.amazonaws:aws-java-sdk-s3:+')
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.GetObjectRequest;

String bucketName=args[0]
String remoteFileName=args[1]

AmazonS3 s3Client = new AmazonS3Client();  
GetObjectRequest request= new GetObjectRequest(bucketName, remoteFileName);
S3Object object = s3Client.getObject(request);

InputStream objectData = object.getObjectContent();
objectData.eachLine { line -> println(line)}
objectData.close();