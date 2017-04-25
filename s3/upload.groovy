@Grab('com.amazonaws:aws-java-sdk-core:+')
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

@Grab('com.amazonaws:aws-java-sdk-s3:+')
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
String bucketName=args[0]
String remoteFileName=args[1]
String localFileName=args[2]

TransferManager tm = new TransferManager(new ProfileCredentialsProvider());        
// TransferManager processes all transfers asynchronously, 
// so this call will return immediately.
Upload upload = tm.upload(
        bucketName, remoteFileName, new File(localFileName));

try {
    // Or you can block and wait for the upload to finish
    upload.waitForCompletion();
    System.out.println("Upload complete.");
} catch (AmazonClientException amazonClientException) {
    System.out.println("Unable to upload file, upload was aborted.");
    amazonClientException.printStackTrace();
}