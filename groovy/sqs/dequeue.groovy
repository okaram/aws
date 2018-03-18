@Grab('com.amazonaws:aws-java-sdk-core:+')
import com.amazonaws.services.sqs.AmazonSQSClient;
@Grab('com.amazonaws:aws-java-sdk-sqs:+')
import com.amazonaws.services.sqs.model.*;

def queue_url=args[0]

AmazonSQSClient client=new AmazonSQSClient();

println("Reading from $queue_url")
int empty_receives=0;
while(true) {
  def result=client.receiveMessage(queue_url)
  if(result.getMessages().size() ==0) {
    break;
  } else {
    for(def mesage : result.getMessages() ) {
      println(mesage.body);
      client.deleteMessage(queue_url, mesage.receiptHandle)
    }
  }
}
println("No more messages available")