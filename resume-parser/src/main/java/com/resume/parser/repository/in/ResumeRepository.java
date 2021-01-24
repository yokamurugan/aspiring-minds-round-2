package com.resume.parser.repository.in;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.resume.parser.model.ResumeModel;


@Repository
public interface ResumeRepository extends MongoRepository<ResumeModel, String> {
	
	@Query(value="{\"$or\":[{" + 
			"        \"parsedData.skills.Technologies\": {\"$regex\" : ?0}" + 
			"    }, {" + 
			"        \"parsedData.skills.Programming Languages\":  {\"$regex\" : ?0}" + 
			"    },{" + 
			"		\"parsedData.skills.Languages\":  {\"$regex\" : ?0}" + 
			"	 },{" + 
			"		\"parsedData.skills.Skills & Expertise\":  {\"$regex\" : ?0}" + 
			"	 },{" + 
			"		\"parsedData.skills.Technical Expertise\":  {\"$regex\" : ?0}" + 
			"	 },{" + 
			"		\"parsedData.skills.Web Technologies\":  {\"$regex\" : ?0}" + 
			"	}"
			+ "]}",fields="{_id : 1, parsedData : 1, extension : 1}")
	  List<ResumeModel> findByTechnology(final String tech);

}
