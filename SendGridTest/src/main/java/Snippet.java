
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

public class Snippet {

	public static void main(String args[]) throws SendGridException {
		 	
    	SendGrid sendgrid = new SendGrid("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");	
	    	SendGrid.Email email = new SendGrid.Email();
	    	email.addTo("teja.tummalapalli@us.ibm.com");
			email.setFromName("test");
			email.setFrom("test@test.com");
			email.setHtml("Hey!! Its just a test mail. Relax");
			email.setSubject("Test Mail");
			SendGrid.Response response = sendgrid.send(email);
		    System.out.println(response.getMessage());
			
	}
	
}
