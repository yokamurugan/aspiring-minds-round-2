package com.resume.parser.controller.in;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.resume.parser.ResponseWrapper;
import com.resume.parser.model.ResumeModel;
import com.resume.parser.service.in.ParserService;
import com.resume.parser.service.in.ResumeService;

@RestController
@CrossOrigin
public class ParserController {

	@Autowired
	private ParserService parserService;

	@Autowired
	private ResumeService resumeService;

	@PostMapping("/upload")
	public ResponseWrapper parseResume(@RequestParam MultipartFile resume) {
		ResponseWrapper responseWrapper = null;
		try {
			responseWrapper = parserService.parseResume(resume);
		} catch (Exception ex) {
			responseWrapper = new ResponseWrapper();
			responseWrapper.setMessage(ex.getMessage());
			responseWrapper.setStatus(500);
			ex.printStackTrace();
		}
		return responseWrapper;
	}

	@PostMapping("/uploadtoDB")
	public ResponseWrapper parseResumetoDB(@RequestParam MultipartFile resume) {
		ResponseWrapper responseWrapper = null;
		try {
			responseWrapper = parserService.parseResume(resume);
		} catch (Exception ex) {
			responseWrapper = new ResponseWrapper();
			responseWrapper.setMessage(ex.getMessage());
			responseWrapper.setStatus(500);
			ex.printStackTrace();
		}
		return responseWrapper;
	}

	@GetMapping("/resume")
	public List<ResumeModel> getResumeModel(@RequestParam String technology) {
		return resumeService.getResumeModelByTechnology(String.join("|", (technology.trim().split("\\s*,[,\\s]*"))));
	}

	@GetMapping("/resume/download")
	public ResponseEntity<?> downloadPDFResource(@RequestParam String id) throws IOException {
		ResumeModel model = resumeService.findById(id.split("\\.")[0]);
		// Get bytes, most important part
		// Write to file

		return ResponseEntity.ok()
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + "\"")
		        .body(model.getResume());
	}

	@ExceptionHandler(MultipartException.class)
	public ResponseWrapper handleMultipartException(Exception ex) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setData("No file uploaded");
		responseWrapper.setMessage("Please upload Resume!!");
		responseWrapper.setStatus(400);
		return responseWrapper;
	}

}
