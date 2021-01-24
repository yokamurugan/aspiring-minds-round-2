package com.resume.parser.serviceimpl.in;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.resume.parser.ResponseWrapper;
import com.resume.parser.model.ResumeModel;
import com.resume.parser.repository.in.ResumeRepository;
import com.resume.parser.service.in.ResumeService;

@Service
public class ResumeServiceImpl implements ResumeService {

	@Autowired
	private ResumeRepository resumeRepository;

	@Override
	public void save(MultipartFile file, ResponseWrapper responseWrapper) {
		// TODO Auto-generated method stub
		ResumeModel model = new ResumeModel();
		model.setId(UUID.randomUUID().toString());
		model.setParsedData((HashMap<String, Object>) responseWrapper.getData());
		try {
			model.setResume(file.getBytes());
			model.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resumeRepository.save(model);
	}

	@Override
	public List<ResumeModel> getResumeModelByTechnology(String technology) {
		// TODO Auto-generated method stub
		return resumeRepository.findByTechnology(technology);
	}

	@Override
	public ResumeModel findById(String id) {
		// TODO Auto-generated method stub
		return resumeRepository.findOne(id);
	}

}
