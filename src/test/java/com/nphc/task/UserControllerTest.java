package com.nphc.task;



import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.junit.jupiter.api.Test;
import com.nphc.task.entity.User;
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
public class UserControllerTest  {
	@LocalServerPort
    int randomServerPort;
	
	
	 @Test
	    public void testAddEmployeeSuccess() throws URISyntaxException 
	    {
	        RestTemplate restTemplate = new RestTemplate();
	        final String baseUrl = "http://localhost:"+randomServerPort+"/users";
	        URI uri = new URI(baseUrl);
	        User employee = new User("e1001", "adam", "Gilly", 10.00,"12/01/2021");
	         
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("X-COM-PERSIST", "true");      
	 
	        HttpEntity<User> request = new HttpEntity<>(employee, headers);
	         
	        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
	         
	        //Verify request succeed
	        Assertions.assertEquals(200, result.getStatusCodeValue());
	    }
	 @Test
	    public void testGetEmployeeListSuccessWithHeaders() throws URISyntaxException 
	    {
	        RestTemplate restTemplate = new RestTemplate();
	         
	        final String baseUrl = "http://localhost:"+randomServerPort+"/users";
	        URI uri = new URI(baseUrl);
	         
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("X-COM-LOCATION", "USA");
	        User employee1 = new User("e1001", "adam", "Gilly", 10.00,"12/01/2021");
	        User employee2 = new User("e1001", "adam", "Gilly", 10.00,"12/01/2021");
	        User employee3 = new User("e1001", "adam", "Gilly", 10.00,"12/01/2021");
	        List<User> users = new ArrayList<>();
	        users.add(employee1);
	        users.add(employee2);
	        users.add(employee3);

	        HttpEntity<List<User>> requestEntity = new HttpEntity<>(users, headers);
	 
	        try
	        {
	        	ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		       // System.out.println(result);
	        	Assertions.assertEquals(204, result.getStatusCodeValue());

	        //	Assertions.assertTrue(true);
	        }
	        catch(HttpClientErrorException ex) 
	        {
	            //Verify bad request and missing header
	            Assertions.assertEquals(400, ex.getRawStatusCode());
	            Assertions.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
	        }
	    }
	  
	
}
