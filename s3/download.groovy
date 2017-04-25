@Grab('com.amazonaws:aws-java-sdk-core:+')
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

@Grab('com.amazonaws:aws-java-sdk-s3:+')
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.GetObjectRequest;

String bucketName=args[0]
String remoteFileName=args[1]


AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());        
S3Object object = s3Client.getObject(
                  new GetObjectRequest(bucketName, remoteFileName));
InputStream objectData = object.getObjectContent();
// Process the objectData stream.

objectData.eachLine { line -> println(line)}
objectData.close();
