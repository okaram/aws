@Grab('com.amazonaws:aws-java-sdk-core:+')
import com.amazonaws.services.sqs.AmazonSQSClient;
@Grab('com.amazonaws:aws-java-sdk-sqs:+')
import com.amazonaws.services.sqs.model.*;

def queue_url=args[0]
def message=args[1]
AmazonSQSClient client=new AmazonSQSClient();

SendMessageRequest request=new SendMessageRequest(queue_url,message);
//request.setMessageDeduplicationId(message.replace(" ",""));
request.setMessageGroupId("group1");

client.sendMessage(request);
