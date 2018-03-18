@Grab('com.amazonaws:aws-java-sdk-core:+')
import com.amazonaws.services.sqs.AmazonSQSClient;
@Grab('com.amazonaws:aws-java-sdk-sqs:+')
import com.amazonaws.services.sqs.model.*;

// groovy has json support, but jackson is more java-ish
@Grab('com.fasterxml.jackson.core:jackson-databind:+')
import com.fasterxml.jackson.databind.ObjectMapper;

class MyThingy {
  int number;
  long nanoTime=System.nanoTime()
}

def queue_url=args[0]
AmazonSQSClient client=new AmazonSQSClient();
ObjectMapper jsonMapper=new ObjectMapper();

while(true) {
  def result=client.receiveMessage(queue_url)
  for(def message : result.getMessages() ) {
    MyThingy mt=jsonMapper.readValue(message.getBody(), MyThingy.class)
    println("${mt.number} - ${(System.nanoTime()-mt.nanoTime)/1000000}");
    client.deleteMessage(queue_url, message.receiptHandle)
  }
}


