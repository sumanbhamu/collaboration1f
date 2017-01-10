package com.suman.restController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.suman.dao.JobDAO;
import com.suman.model.Job;
import com.suman.model.JobApplied;

@RestController
public class JobController {
	
	@Autowired
	Job job;
	
	@Autowired
	JobApplied jobApplied;
	
	@Autowired
	JobDAO jobDAO;
	
	@RequestMapping(value="/getAllJobs/",method=RequestMethod.GET)
	public ResponseEntity<List<Job>> getAllAvailableJob()
	{
		
		List<Job> jobs=jobDAO.getAllAvailableJobs();
		if(jobs.isEmpty()){
			Job j=new Job();
			j.setErrorCode("404");
		    j.setErrorMessage("No jobs are available...in controller");			
			return new ResponseEntity<List<Job>>(HttpStatus.OK);
		}
		job.setErrorCode("200");
		job.setErrorMessage("u hv successfully got all the jobs..");
		
		System.out.println("get all jobs in job controller.java");
		return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getMyAppliedJobs/",method=RequestMethod.GET)
	public ResponseEntity<List<Job>> getMyAppliedJob(HttpSession httpSession)
	{
		String loggedInUserId=(String) httpSession.getAttribute("loggedInUserId");
		List<Job> jobs=new ArrayList<Job>();
		if(loggedInUserId==null || loggedInUserId.isEmpty())
		{
			job.setErrorCode("404");
			job.setErrorMessage("u hv to login to see ur applied jobs..");
			// to add this msg
			jobs.add(job);
		}
		else{
			job.setErrorCode("200");
			job.setErrorMessage("u hv successfully recieved ...in job controller.java.");
			
			jobs=jobDAO.getMyAppliedJobs(loggedInUserId);
			
		}
		return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value="/getJobDetails/{jobId}",method=RequestMethod.GET)
	public ResponseEntity<Job> getJobDetails(@PathVariable("jobId")String jobId)
	{
		Job job=jobDAO.getJobDetails(jobId);
		if(job==null){
			job=new Job();
			job.setErrorCode("404");
			job.setErrorMessage("job does not exists with id:" + jobId);
			return new ResponseEntity<Job> (job,HttpStatus.OK);
		}

		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/postAJob/",method=RequestMethod.POST)
	public ResponseEntity<Job> postAJob(@RequestBody Job job){
		job.setStatus('v');                         //V-vacant  F-filled  p-pending
		if(jobDAO.save(job)==false)
		{
			job.setErrorCode("404");
			job.setErrorMessage("not able to post job..");
			System.out.println("not able to post job.....");
			
		}
		else{
			job.setErrorCode("200");
			job.setErrorMessage("successfully posted the job..");
			System.out.println("successfully posted the job..");
		}
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/applyForJob/{jobId}",method=RequestMethod.POST)
	public ResponseEntity<JobApplied>  applyForJob(@PathVariable("jobId")String jobId,HttpSession httpSession)
	{
		String loggedInUserId=(String) httpSession.getAttribute("loggedInUserId");
		
		
		if(loggedInUserId==null || loggedInUserId.isEmpty())
		{
			job.setErrorCode("404");
			job.setErrorMessage("u hv to login to see ur apply jobs..");
			
		}
		else
		{
			jobApplied.setJob_id(jobId);
			jobApplied.setUser_id(loggedInUserId);
			jobApplied.setStatus('N'); //newly applied ; c-call for interview ; s-selected
			if(jobDAO.save(jobApplied)==false)
			{
				job.setErrorCode("404");
				job.setErrorMessage("not able apply for job....");
				System.out.println("not able apply for job....in job controller.java");
			}
			
		}
		
		return new ResponseEntity<JobApplied>(jobApplied,HttpStatus.OK);
	}
	
		
	
	
	
	
	@RequestMapping(value="/selectUser/{userId}/{jobId}/{remarks}",method=RequestMethod.PUT)
	public ResponseEntity<Job>  selectUser(@PathVariable("userId")String userId,@PathVariable("jobId")String jobId
			,@PathVariable("remarks")String remarks,HttpSession httpSession)
	{
		jobApplied=jobDAO.getJobApplied(userId, jobId);
			
		 //newly applied ; c-call for interview ; s-selected
		jobApplied=updateJobApplicationStatus(userId, jobId,'S',remarks);	
		
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/callForInterview/{userId}/{jobId}/{remarks}",method=RequestMethod.PUT)
	public ResponseEntity<Job>  callForInterview(@PathVariable("userId")String userId,@PathVariable("jobId")String jobId
			,@PathVariable("remarks")String remarks,HttpSession httpSession)
	{
		jobApplied=jobDAO.getJobApplied(userId, jobId);
			
		jobApplied=updateJobApplicationStatus(userId, jobId,'C',remarks);
		
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
	
	
	
	
	
	@RequestMapping(value="/rejectJobApplication/{userId}/{jobId}/{remarks}",method=RequestMethod.PUT)
	public ResponseEntity<Job>  rejectJobApplication(@PathVariable("userId")String userId,@PathVariable("jobId")String jobId
			,@PathVariable("remarks")String remarks,HttpSession httpSession)
	{
		jobApplied=jobDAO.getJobApplied(userId, jobId); //get particular row
			
				
		jobApplied=updateJobApplicationStatus(userId, jobId,'R',remarks);
		
		
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
	
	
	
		
	
	private JobApplied updateJobApplicationStatus(String userId,String jobId,char status,String remarks)
	{
		
		jobApplied=jobDAO.getJobApplied(userId, jobId);
		
		jobApplied.setStatus(status);
		jobApplied.setRemark(remarks);
		
		if(jobDAO.update(jobApplied)==false){
			job.setErrorCode("404");
			job.setErrorMessage("not able to change the application status to .."+status);
			System.out.println("not able to change the application status to .."+status+"in job controller,java");
			
		}
		else{
			job.setErrorCode("200");
			job.setErrorMessage("successfully  changed the application status to "+status);
			System.out.println("successfully  changed the application status to "+status +"in job controller,java");
			
		}
		return jobApplied;
	}
		
		
	

}
