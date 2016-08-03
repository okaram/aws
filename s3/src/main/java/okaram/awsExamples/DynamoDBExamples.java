package okaram.awsExamples;


import java.text.MessageFormat;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.function.BiConsumer;

public class DynamoDBExamples {
	
	public static class CounterDao {
		public static class CounterException extends Exception {
			public boolean counterExists;
			public CounterException(String message, boolean counterExists) {
				super(message);
				this.counterExists=counterExists;
			}
			public boolean getCounterExists() {
				return counterExists;
			}
		}
		
		public AmazonDynamoDB db;
		public String tableName;
		public CounterDao(AmazonDynamoDB db, String tableName)
		{
			this.db=db;
			this.tableName=tableName;
		}
		
		public long getCounterValue(String counter) throws CounterException {
			GetItemRequest req=new GetItemRequest().withTableName(tableName).addKeyEntry("name",new AttributeValue().withS(counter));
			GetItemResult res=db.getItem(req);
			System.out.println(MessageFormat.format("getCounterValue {0} {1}", res, res.getItem()));
			if(res.getItem()==null)
				throw new CounterException("Counter not found",false);
			return Long.parseLong(res.getItem().get("value").getN());	
		}
		
		public long incrementCounter(String counter) {
			Map<String, AttributeValue> key=new HashMap<String, AttributeValue>() ;
			key.put("name", new AttributeValue().withS(counter));
			UpdateItemRequest req=new UpdateItemRequest()
					.withTableName(tableName)
					.addExpressionAttributeNamesEntry("#v","value")
					.addExpressionAttributeValuesEntry(":inc", new AttributeValue().withN("1"))
					.withKey(key)
					.withUpdateExpression("ADD #v :inc")
					.withReturnValues(ReturnValue.UPDATED_NEW);
				;
			UpdateItemResult up=db.updateItem(req);
			System.out.println("Update got"+up);
			return Long.parseLong(up.getAttributes().get("value").getN());	
		}
		
		public void createCounter(String counterName) {
			PutItemRequest req=new PutItemRequest()
					.withTableName(tableName)
					.addItemEntry("name", new AttributeValue().withS(counterName))
					.addItemEntry("value", new AttributeValue().withN("0"))
				;
			PutItemResult res=db.putItem(req);
			System.out.println("Put got"+res);
			
		}
	}
	
	
	public static class Redirect {
		String userId, redirectId, url;
		long hits;
		@Override
		public String toString() {
			return "Redirect [userId=" + userId + ", redirectId=" + redirectId + ", url=" + url + ", hits=" + hits
					+ "]";
		}
		
		
	}
	
	public static class RedirectDao {
		public AmazonDynamoDB db;
		public String tableName;
		
		public RedirectDao(AmazonDynamoDB db, String tableName) {
			super();
			this.db = db;
			this.tableName = tableName;
		}

		public Redirect getAndIncrementRedirect(String userId, String redirectId) {
			Map<String, AttributeValue> key=new HashMap<String, AttributeValue>() ;
			key.put("userId", new AttributeValue().withS(userId));
			key.put("redirectId", new AttributeValue().withS(redirectId));
			
			UpdateItemRequest req=new UpdateItemRequest()
					.withTableName(tableName)
					.addExpressionAttributeNamesEntry("#v","hits")
					.addExpressionAttributeValuesEntry(":inc", new AttributeValue().withN("1"))
					.withKey(key)
					.withUpdateExpression("ADD #v :inc")
					.withReturnValues(ReturnValue.ALL_NEW);
			;
			UpdateItemResult up=db.updateItem(req);
			Redirect r=new Redirect();
			r.hits=Long.parseLong(up.getAttributes().get("hits").getN());
			r.url=up.getAttributes().get("url").getS();
			r.userId=userId;
			r.redirectId=redirectId;
			return r;
		}
		
		public List<Redirect> getRedirectsForUser(String userId) {
			Map<String, AttributeValue> key=new HashMap<String, AttributeValue>() ;
			key.put("userId", new AttributeValue().withS(userId));
			
			QueryRequest req=new QueryRequest()
					.withTableName(tableName)
					.withKeyConditionExpression("userId = :uId")
					.addExpressionAttributeValuesEntry(":uId", new AttributeValue().withS(userId))
			;
			QueryResult res=db.query(req);
			System.out.println("Query result: "+res);
			return null;
		}
	}
	
	@DynamoDBTable(tableName = "items")
	public static class MyItem {		
		private int id;
		private String name;

		public MyItem() {}

			public MyItem(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		@DynamoDBHashKey
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		@DynamoDBAttribute
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "MyItem [id=" + id + ", name=" + name + "]";
		}		
	}
	
	public static void describeTable(AmazonDynamoDB dynamoDB, String tableName)
	{
		DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
        TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
        System.out.println("Table Description: " + tableDescription);
	}
	
	public static void getItemSample(AmazonDynamoDB dynamoDB,int id) {
		GetItemRequest req=new GetItemRequest().withTableName("items").addKeyEntry("id",new AttributeValue().withN(""+id));
		GetItemResult res=dynamoDB.getItem(req);
		System.out.println("getCV Result: "+res);
	}
	
	public static MyItem getItemWithMapper(AmazonDynamoDB dynamoDB, int id)
	{
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
		return mapper.load(MyItem.class,id,null);
	}
	
	public static void putItemWithMapper(AmazonDynamoDB dynamoDB, MyItem itm)
	{
		DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
		mapper.save(itm);
	}

	public static void getAndPrint(AmazonDynamoDB dDB, String[] args) {
		int id=Integer.parseInt(args[1]);
		MyItem itm=getItemWithMapper(dDB, id);
		System.out.println(itm);
	}
	
	public static void describeTable(AmazonDynamoDB dDB, String[] args) {
		describeTable(dDB,args[1]);
	}

	public static void putItem(AmazonDynamoDB dDB, String[] args) {
		int id=Integer.parseInt(args[1]);
		MyItem itm=new MyItem(id,args[2]);
		putItemWithMapper(dDB,itm);
	}

	public static void getCounter(AmazonDynamoDB db, String[] args) {
		CounterDao cd=new CounterDao(db,"counters");
		try {
			Long val=cd.getCounterValue(args[1]);
			System.out.println("Value is:"+val);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	public static void incCounter(AmazonDynamoDB db, String[] args) {
		CounterDao cd=new CounterDao(db,"counters");
		Long val=cd.incrementCounter(args[1]);
		System.out.println("Value is:"+val);
	}
	
	public static void createCounter(AmazonDynamoDB db, String[] args) {
		CounterDao cd=new CounterDao(db,"counters");
		cd.createCounter(args[1]);
	}

	public static void getAndIncrementRedirect(AmazonDynamoDB db, String[] args) 
	{
		RedirectDao rd=new RedirectDao(db,"redirects");
		System.out.println(rd.getAndIncrementRedirect(args[1], args[2]));
	}

	public static void getRedirectsForUser(AmazonDynamoDB db, String[] args) 
	{
		RedirectDao rd=new RedirectDao(db,"redirects");
		System.out.println(rd.getRedirectsForUser(args[1]));
	}

	public static void main(String[] args) {
		AmazonDynamoDBClient dynamoDB=new AmazonDynamoDBClient();
		
		Map<String, BiConsumer<AmazonDynamoDB,String[]>> argHandlers=new HashMap<String, BiConsumer<AmazonDynamoDB,String[]>>();
		argHandlers.put("get", DynamoDBExamples::getAndPrint);
		argHandlers.put("put", DynamoDBExamples::putItem);
		argHandlers.put("desc", DynamoDBExamples::describeTable);
		argHandlers.put("getc", DynamoDBExamples::getCounter);
		argHandlers.put("incc", DynamoDBExamples::incCounter);
		argHandlers.put("createc", DynamoDBExamples::createCounter);

		argHandlers.put("incr", DynamoDBExamples::getAndIncrementRedirect);
		argHandlers.put("getrs", DynamoDBExamples::getRedirectsForUser);
		
		if(argHandlers.containsKey(args[0])) {
			argHandlers.get(args[0]).accept(dynamoDB, args);
		} else {
			System.out.println("Command not understood");
		}
	}

}
