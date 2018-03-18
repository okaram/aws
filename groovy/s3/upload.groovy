@Grab('com.amazonaws:aws-java-sdk-s3:+')
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
String bucketName=args[0]
String remoteFileName=args[1]
String localFileName=args[2]

TransferManager tm = new TransferManager();        
// TransferManager processes all transfers asynchronously, 
// so this call will return immediately.
Upload upload = tm.upload(bucketName, remoteFileName, new File(localFileName));
// this blocks until the transfer is done
upload.waitForCompletion();
System.out.println("Upload complete.");
// the TransferManager creates threads, so if we don't exit it will run forever
tm.shutdownNow();
