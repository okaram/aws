
@Grab('com.amazonaws:aws-java-sdk:+')
import com.amazonaws.services.kinesis.model.*
@Grab('com.amazonaws:aws-java-sdk-kinesis:+')
@Grab('com.amazonaws:amazon-kinesis-client:+')
import com.amazonaws.services.kinesis.*

import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder


AmazonKinesis client=com.amazonaws.services.kinesis.AmazonKinesisClientBuilder.defaultClient();


