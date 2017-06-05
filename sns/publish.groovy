@Grab('com.amazonaws:aws-java-sdk-core:+')
import com.amazonaws.services.sns.AmazonSNSClient;

@Grab('com.amazonaws:aws-java-sdk-sns:+')
import com.amazonaws.services.sns.model.*;

def topic_arn=args[0]
def message=args[1]
AmazonSNSClient client=new AmazonSNSClient();
client.publish(topic_arn, message);
