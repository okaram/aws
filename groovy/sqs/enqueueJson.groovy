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
def start=Integer.parseInt(args[1])
def end=Integer.parseInt(args[2])
AmazonSQSClient client=new AmazonSQSClient();
ObjectMapper jsonMapper=new ObjectMapper();

for (int i=start; i<end; ++i) {
  println("Enqueing $i")
  MyThingy mt=new MyThingy()
  mt.number=i;
  client.sendMessage(queue_url, jsonMapper.writeValueAsString(mt));
}
