package com.resume.parser.model;

import java.util.HashMap;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="resume")
public class ResumeModel {

	@Id
	private String id;
	
	private HashMap<String,Object> parsedData;
	
	private byte[] resume;
	
	private String extension;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, Object> getParsedData() {
		return parsedData;
	}

	public void setParsedData(HashMap<String, Object> parsedData) {
		this.parsedData = parsedData;
	}

	public byte[] getResume() {
		return resume;
	}

	public void setResume(byte[] resume) {
		this.resume = resume;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	
	
}
