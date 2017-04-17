package test;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class JSONSender {

public static void main(String[] args) {

JSONSender theSender=new JSONSender();

theSender.sendMessage();

}

void sendMessage(){

HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

try {

HttpPost request = new HttpPost("https://nrlgeoint.cs.uno.edu/JsonValidator/sensordata");

StringEntity params =new StringEntity("{ \"MessageID\":\"11\",\"SensorID\":\"1\",\"TimeOfDetect\":3200,\"DetectLocation\":{\"latitude\":32.5, \"longitude\":78.5}, \"SensorLocation\":{\"latitude\":32.5, \"longitude\":78.5},\"CCIR\":\"Maneuver Threat\", \"DetectType\":\"Unknown\", \"EntityID\":\"12\", \"Count\":2}");

request.addHeader("content-type", "application/x-www-form-urlencoded");
request.addHeader("content-type", "application/json");

request.setEntity(params);

HttpResponse response = httpClient.execute(request);

HttpEntity entity = response.getEntity();
String responseString = EntityUtils.toString(entity, "UTF-8");
System.out.println(responseString );
}catch (Exception ex) {

ex.printStackTrace();

//handle exception here

}

}

}



