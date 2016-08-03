package okaram.awsExamples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.nio.file.*;

public class S3Examples {

    public static void listAllBuckets(AmazonS3 s3)
    {
 	    for (Bucket bucket : s3.listBuckets()) {
	        System.out.println(" - " + bucket.getName());
        }
    }

    public static void downloadFile(AmazonS3 s3, String bucketName, String fileName, String localFilePath) throws IOException
    {
	    System.out.println("Downloading an object");
        S3Object object = s3.getObject(new GetObjectRequest(bucketName, fileName));
        System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
        Files.copy(object.getObjectContent(), FileSystems.getDefault().getPath(localFilePath));
    }

    public static void main(String[] args) throws IOException
    {
        AmazonS3 s3 = new AmazonS3Client();
        //listAllBuckets(s3);
        downloadFile(s3,"okaram-test","test.txt","test1.txt");
    }
}