
@Grab('com.amazonaws:aws-java-sdk-s3:+')
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

String bucketName=args[0]
AmazonS3 s3client = new AmazonS3Client(); 
final ListObjectsV2Request request = new ListObjectsV2Request()
          .withBucketName(bucketName)
          .withMaxKeys(2);
ListObjectsV2Result result;
while(true){               
    result = s3client.listObjectsV2(request);
    
    for (S3ObjectSummary objectSummary : 
        result.getObjectSummaries()) {
        System.out.println(" - " + objectSummary.getKey() + "  " +
                "(size = " + objectSummary.getSize() + 
                ")");
    }
    request.setContinuationToken(result.getNextContinuationToken());
    if(!result.isTruncated())
      break;
}