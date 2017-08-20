package com.bam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bam.bean.Curriculum;
import com.bam.repository.CurriculumRepository;

@Transactional
public class CurriculumService {

	@Autowired
	CurriculumRepository curriculumRepository;
	
	public List<Curriculum> getAllCurriculum(){
		List<Curriculum> curriculumList =  curriculumRepository.findAll();
		//obfuscate password
		for(Curriculum element : curriculumList){
			element.getCurriculum_Creator().setPwd("");
			if(element.getCurriculumModifier() != null)
				element.getCurriculumModifier().setPwd("");
		}
		return curriculumList;
	}
	
	public Curriculum getCuricullumById(Integer id){
		//obfuscate password
		Curriculum curriculum = curriculumRepository.findById(id);
		curriculum.getCurriculum_Creator().setPwd("");
		if(curriculum.getCurriculumModifier() != null)
			curriculum.getCurriculumModifier().setPwd("");
		return curriculum;
	}
	
	public void save(Curriculum c){
		curriculumRepository.save(c);
	}
	
}
