package com.suman.dao;

import java.util.List;

import com.suman.model.Job;

public interface JobDAO {

	
	public boolean save(Job job); //posting/adding a job

	public boolean update(Job job); //updating job
	
	public Job get(String jobID);
	
	public List<Job> list();
	
	public boolean delete(Job job);

}
