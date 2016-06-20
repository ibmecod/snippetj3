
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.list.AccountList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public class Snippet {
 
  // Find your Account Sid and Token at twilio.com/user/account
  public static final String ACCOUNT_SID = "AC1f81b3588d2c1a03f47e001780448d8b";
  public static final String AUTH_TOKEN = "655306de128950a628b9aa5d58c8b763";
 
  public static void main(String[] args) throws TwilioRestException {
    TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
 
    // Build a filter for the MessageList
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("Body", "Hi Good Morning"));
    params.add(new BasicNameValuePair("To", "+19196412386"));
    params.add(new BasicNameValuePair("From", "+19199481368"));
 
    MessageFactory messageFactory = client.getAccount().getMessageFactory();
    Message message = messageFactory.create(params);
    System.out.println(message.getSid());
  
 	// Get the main account (The one we used to authenticate the client)
 	Account mainAccount = client.getAccount();
 			
 	// Make a call
	CallFactory callFactory = mainAccount.getCallFactory();
	Map<String, String> callParams = new HashMap<String, String>();
	callParams.put("To", "19196412386"); // Replace with a valid phone number
	callParams.put("From", "19199481368"); // Replace with a valid phone
	// number in your account
	callParams.put("Url", "http://demo.twilio.com/welcome/voice/");
	Call call = callFactory.create(callParams);
	System.out.println(call.getSid());


    
  }
}