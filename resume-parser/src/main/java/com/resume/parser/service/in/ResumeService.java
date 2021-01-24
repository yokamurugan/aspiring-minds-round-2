package com.resume.parser.service.in;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.resume.parser.ResponseWrapper;
import com.resume.parser.model.ResumeModel;


public interface ResumeService {

	void save(MultipartFile file, ResponseWrapper responseWrapper);

	List<ResumeModel> getResumeModelByTechnology(String technology);

	ResumeModel findById(String id);

}
