package com.idontchop.usagelimitservice.dtos;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;


/**
 * Simple RestMessage with builder pattern.
 * 
 * .build(String) - creates a "message" field
 * .add(key,value) - adds additional fields
 * 
 * @author micro
 *
 */
public class RestMessage {
	
	Map<String,String> messages = new HashMap<>();

	public static RestMessage build (String message) {
		
		return new RestMessage(message);
	}
	
	public static RestMessage build ( Map<String,String> messages) {
		RestMessage rm = new RestMessage();
		rm.setMessages(messages);
		return rm;
	}
	
	public static RestMessage empty() {
		return new RestMessage();
	}
	
	public RestMessage () {}
	public RestMessage (String message) {
		messages.put("message", message);
	}
	
	public RestMessage add (String key, String value ) {
		messages.put(key,value);
		return this;
	}
	
	@JsonAnyGetter
	public Map<String, String> getMessages() {
		return messages;
	}
	
	
	public void setMessages(Map<String, String> messages) {
		this.messages = messages;
	}

	
}

