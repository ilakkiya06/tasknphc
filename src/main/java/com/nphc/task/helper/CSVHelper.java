package com.nphc.task.helper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.nphc.task.entity.User;
public class CSVHelper {
	public static String TYPE = "text/csv";
	static String[] HEADERs = { "id", "login", "name", "salary","startDate" };

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}
	
	public static List<User> csvToUsers(InputStream is) {
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        CSVParser csvParser = new CSVParser(fileReader,
	            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

	      List<User> users = new ArrayList<User>();

	      List<CSVRecord> csvRecords = csvParser.getRecords();

	      for (CSVRecord csvRecord : csvRecords) {
	    	  String id = csvRecord.get("id");
	    	  String login =  csvRecord.get("login");
	    	  String name  = csvRecord.get("name");
	    	  byte[] bytes = name.getBytes(StandardCharsets.UTF_8);
	    	  String utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);
	    	  Double salary = Double.parseDouble(csvRecord.get("salary"));
	    	 boolean isValidDate = ParameterValidator.checkCSVDateIsValid(csvRecord.get("startDate"));
	
	    	  User user;
			if (! id.isEmpty() && !login.isEmpty() && !utf8EncodedString.isEmpty() && isValidDate && salary>0.00 ) {
				user = new User(csvRecord.get("id"), csvRecord.get("login"), utf8EncodedString,
						Double.parseDouble(csvRecord.get("salary")), csvRecord.get("startDate"));
		    	  users.add(user);

			}else throw new RuntimeException("fail to parse CSV file: " );
	      }

	      return users;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
	    }
	  }

	

}
