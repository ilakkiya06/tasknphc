package com.nphc.task.message;

import java.util.List;

import com.nphc.task.entity.User;

public class GetAllUsersResponse {
	
	List<User> results;
	public GetAllUsersResponse() {
		
	}

	public GetAllUsersResponse(List<User> results) {
		super();
		this.results = results;
	}

	public List<User> getResults() {
		return results;
	}

	public void setResults(List<User> results) {
		this.results = results;
	}

}
