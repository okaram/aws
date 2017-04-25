@Grapes([
    @Grab(group='org.slf4j', module='slf4j-api', version='1.6.1'),
    @Grab(group='ch.qos.logback', module='logback-classic', version='0.9.28')
])
import org.slf4j.*

@Grab('com.amazonaws:aws-java-sdk-core:1.10.0')
@Grab('com.amazonaws:amazon-kinesis-producer:+')
import com.amazonaws.services.kinesis.producer.*

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

import java.nio.ByteBuffer;
KinesisProducerConfiguration config=new KinesisProducerConfiguration();
config.setRegion("us-east-1");
config.setCredentialsProvider(new DefaultAWSCredentialsProviderChain());


KinesisProducer kinesis = new KinesisProducer(config);  
// Put some records
ByteBuffer data = ByteBuffer.wrap("myData".getBytes("UTF-8"));
// doesn't block       
java.util.concurrent.Future<UserRecordResult>  future=kinesis.addUserRecord("myStream", "myPartitionKey", data); 

println( future.get()); 

