package com.suman.dao;

import java.util.List;

import com.suman.model.Job;
import com.suman.model.JobApplied;

public interface JobDAO {

	
	public boolean save(Job job); //posting/adding a job	
	public boolean save(JobApplied jobApplied); //applying for job

	public boolean update(Job job); //updating job	
	public boolean update(JobApplied jobApplied); //updating job application
	
	public List<Job> getAllAvailableJobs(); //list opened jobs
		
	public Job getJobDetails(String id);  // get all the job details
	
	public List<Job> getMyAppliedJobs(String userId); //listing jobs of a particular user
	
	
	public JobApplied getJobApplied(String userId,String jobId); //get particular user's applied 
	
	public JobApplied getJobApplied(String jobId); //get all the job applications
	
	public boolean delete(Job job);

}
