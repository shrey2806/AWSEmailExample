package com.example;

import java.io.IOException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class AWSEmailService {

	static SendEmailRequest setEmailRequest(String TO, String FROM, String BODY, String SUBJECT) {
		
		// Adding Destination of Email.
		// Here One recipient is added , multiple recipients can be added at same time. 
		
		Destination destination = new Destination().withToAddresses(new String[] {TO});
		
		// Creating Subject and Body
		Content subject = new Content().withData(SUBJECT);
		Content textBody = new Content().withData(BODY);
		Body body = new Body().withText(textBody);
		
		// Create Message with subject and body;
		Message message = new Message().withSubject(subject).withBody(body);
		
		return new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);
		
		
	}
	
    public static void main(String args[])throws IOException{
		
	
	
		SendEmailRequest request = setEmailRequest(EmailUtil.TO,EmailUtil.FROM, EmailUtil.BODY,EmailUtil.SUBJECT);
		
		try {
			// 
			ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
			
			try {
				
				credentialsProvider.getCredentials();
			}catch(Exception ex) {
				 throw new AmazonClientException(
	                        "Cannot load the credentials from the credential profiles file. " + ex);
			}
			
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
		                .withCredentials(credentialsProvider).withRegion("ap-south-1").build();
			client.sendEmail(request);
			System.out.println("Congrats!! Email is Sent");
			   
		}catch(Exception ex) {
			System.out.println("Email is not sent, Some error Occured");
			System.out.println("Error:" + ex.getMessage() );
			
		}
		
	}

}
