@Grab('com.amazonaws:aws-java-sdk-core:+')
import com.amazonaws.services.sqs.AmazonSQSClient;
@Grab('com.amazonaws:aws-java-sdk-sqs:+')
import com.amazonaws.services.sqs.model.*;

def queue_url=args[0]
def start=Integer.parseInt(args[1])
def end=Integer.parseInt(args[2])
AmazonSQSClient client=new AmazonSQSClient();
for (int i=start; i<end; ++i) {
  println("Enqueing $i")
  client.sendMessage(queue_url, new Integer(i).toString());
}
