@Grab('com.amazonaws:aws-java-sdk:+')
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClient
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest
import com.amazonaws.services.cloudwatch.model.MetricDatum
String namespace=args[0]
String metricName=args[1]
String unit=args[2]
double value=Double.parseDouble(args[3])
long time=Long.parseLong(args[4])

AmazonCloudWatchAsyncClient cwClient=new AmazonCloudWatchAsyncClient();
Date now=new Date(time);
MetricDatum datum=new MetricDatum().withUnit(unit).
    withValue(value).withTimestamp(now).
    withMetricName(metricName);
PutMetricDataRequest req=new PutMetricDataRequest().
    withNamespace(namespace).
    withMetricData(datum)

def futureAns=cwClient.putMetricDataAsync(req)
while(!futureAns.isDone()) {
  println('not done')
  sleep(1000);
}
println('done !')
println(futureAns)
println(futureAns.get())
System.exit(0); // to terminate blocking threads