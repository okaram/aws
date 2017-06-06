@Grab('com.amazonaws:aws-java-sdk-core:+')
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

@Grab('com.amazonaws:aws-java-sdk-s3:+')
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

String bucketName=args[0]
AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider()); 
final ListObjectsV2Request req = new ListObjectsV2Request()
          .withBucketName(bucketName)
          .withMaxKeys(2);
ListObjectsV2Result result;
while(true){               
    result = s3client.listObjectsV2(req);
    
    for (S3ObjectSummary objectSummary : 
        result.getObjectSummaries()) {
        System.out.println(" - " + objectSummary.getKey() + "  " +
                "(size = " + objectSummary.getSize() + 
                ")");
    }
//    System.out.println("Next Continuation Token : " + result.getNextContinuationToken());
    req.setContinuationToken(result.getNextContinuationToken());
    if(!result.isTruncated())
      break;
}