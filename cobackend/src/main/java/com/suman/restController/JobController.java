package com.suman.restController;

import java.sql.Date;
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

	@Autowired
	HttpSession httpSession;

	@RequestMapping(value = "/getAllJobs/", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getAllAvailableJob() {

		List<Job> jobs = jobDAO.getAllAvailableJobs();

		/*
		 * if(jobs.isEmpty()){ //Job j1=new Job(); job.setErrorCode("404");
		 * job.setErrorMessage("No jobs are available...in controller"); return
		 * new ResponseEntity<List<Job>>(HttpStatus.OK); }
		 * job.setErrorCode("200");
		 * job.setErrorMessage("u hv successfully got all the vacant jobs..");
		 */
		System.out.println("get all jobs in job controller.java");
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMyAppliedJobs/", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getMyAppliedJob() {
		String loggedInUserId = (String) httpSession.getAttribute("loggedInUserId");
		List<Job> jobs = new ArrayList<Job>();

		if (loggedInUserId == null || loggedInUserId.isEmpty()) {
			job.setErrorCode("404");
			job.setErrorMessage("u hv to login to see ur applied jobs..");
			// to add this msg
			jobs.add(job);
		} else {
			job.setErrorCode("200");
			job.setErrorMessage("u hv successfully recieved ...");
			System.out.println("u hv successfully recieved ...in job controller.java.");
			jobs = jobDAO.getMyAppliedJobs(loggedInUserId);

		}
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}

	@RequestMapping(value = "/getJobDetails/{jobId}", method = RequestMethod.GET)
	public ResponseEntity<Job> getJobDetails(@PathVariable("jobId") String jobId) {
		Job job = jobDAO.getJobDetails(jobId);
		if (job == null) {
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("job does not exists with id:" + jobId);
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}

		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/postAJob", method = RequestMethod.POST)
	public ResponseEntity<Job> postAJob(@RequestBody Job job) {
		job.setStatus('v'); // V-vacant F-filled p-pending
		if (jobDAO.save(job) == false) {
			job.setErrorCode("404");
			job.setErrorMessage("not able to post job..");
			System.out.println("not able to post job.....");

		} else {
			job.setErrorCode("200");
			job.setErrorMessage("successfully posted the job..");
			System.out.println("successfully posted the job..");
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/applyForJob/{jobId}", method = RequestMethod.POST)
	public ResponseEntity<JobApplied> applyForJob(@PathVariable("jobId") String jobId) {
		String loggeddInUserId = (String) httpSession.getAttribute("loggedInUserID");

		if (loggeddInUserId == null || loggeddInUserId.isEmpty()) {
			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage("u hv to not login to see ur apply jobs.."+jobId);

		} else {
			if (isUserAppliedForTheJob(loggeddInUserId, jobId) == false) {  // user has not applied for the job

				jobApplied.setJobid(jobId);
				jobApplied.setUserid(loggeddInUserId);
				jobApplied.setStatus('N'); // newly applied ; c-call for
											// interview ; s-selected
				jobApplied.setDate_applied(new Date(System.currentTimeMillis()));
				if (jobDAO.save(jobApplied)) {
					jobApplied.setErrorCode("200");
					jobApplied.setErrorMessage("u hv successfully  applied for the job...." + jobId);
					System.out.println(" able to apply for job....in job controller.java .." + jobId);
				}
			}

			else {
				jobApplied.setErrorCode("404");
				jobApplied.setErrorMessage("already applied for the job...." + jobId);
				System.out.println("already applied for the job....in job controller.java .." + jobId);

			}
		}

		return new ResponseEntity<JobApplied>(jobApplied, HttpStatus.OK);   //returning obj
	}

	@RequestMapping(value = "/selectUser/{userId}/{jobId}/{remarks}", method = RequestMethod.PUT)
	public ResponseEntity<JobApplied> selectUser(@PathVariable("userId") String userId,
			@PathVariable("jobId") String jobId, @PathVariable("remarks") String remarks, HttpSession httpSession) {
		jobApplied = jobDAO.getJobApplied(userId, jobId);

		// newly applied ; c-call for interview ; s-selected
		jobApplied = updateJobApplicationStatus(userId, jobId, 'S', remarks);

		return new ResponseEntity<JobApplied>(jobApplied, HttpStatus.OK);
	}

	@RequestMapping(value = "/callForInterview/{userId}/{jobId}/{remarks}", method = RequestMethod.PUT)
	public ResponseEntity<JobApplied> callForInterview(@PathVariable("userId") String userId,
			@PathVariable("jobId") String jobId, @PathVariable("remarks") String remarks, HttpSession httpSession) {
		jobApplied = jobDAO.getJobApplied(userId, jobId);

		jobApplied = updateJobApplicationStatus(userId, jobId, 'C', remarks);

		return new ResponseEntity<JobApplied>(jobApplied, HttpStatus.OK);
	}

	@RequestMapping(value = "/rejectJobApplication/{userId}/{jobId}/{remarks}", method = RequestMethod.PUT)
	public ResponseEntity<JobApplied> rejectJobApplication(@PathVariable("userId") String userId,
			@PathVariable("jobId") String jobId, @PathVariable("remarks") String remarks, HttpSession httpSession) {
		jobApplied = jobDAO.getJobApplied(userId, jobId); // get particular row

		jobApplied = updateJobApplicationStatus(userId, jobId, 'R', remarks);

		return new ResponseEntity<JobApplied>(jobApplied, HttpStatus.OK);
	}

	private JobApplied updateJobApplicationStatus(String userId, String jobId, char status, String remarks) {

		if (isUserAppliedForTheJob(userId, jobId) == false) {

			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage(userId + " not applied for the job .." + jobId);
			System.out.println(userId + " not applied for the job ..in controller.." + jobId);
			return jobApplied;

		}

		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");// from
																						// usercontroller.java
		if (loggedInUserRole == null || loggedInUserRole.isEmpty()) { // 1st
																		// need
																		// to
																		// login
			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage("u r not loggedin..");
			System.out.println("u r not logged in job controller.java.");
			return jobApplied;

		}
		if (!loggedInUserRole.equalsIgnoreCase("admin")) // if not admin
		{
			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage("u r not admin to do this operation ..");
			System.out.println("u r not admin to do this operation .in job controller.java.");
			return jobApplied;
		}

		jobApplied = jobDAO.getJobApplied(userId, jobId);

		jobApplied.setStatus(status);
		jobApplied.setRemark(remarks);

		if (jobDAO.update(jobApplied) == false) {
			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage("not able to change the application status to .." + status);
			System.out.println("not able to change the application status to .." + status + "in job controller,java");

		} else {
			jobApplied.setErrorCode("200");
			jobApplied.setErrorMessage("successfully  changed the application status to " + status);
			System.out.println("successfully  changed the application status to " + status + "in job controller,java");

		}
		return jobApplied;
	}

	private boolean isUserAppliedForTheJob(String userId, String jobId) { // check
																			// whether
																			// user
																			// has
																			// applied
																			// for
																			// job
																			// or
																			// not

		if (jobDAO.getJobApplied(userId, jobId) == null) {
			return false;
		}
		return true;

	}

}
