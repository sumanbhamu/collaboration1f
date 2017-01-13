package com.suman.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.suman.model.Job;
import com.suman.model.JobApplied;

@Repository(value = "JobDAO")
public class JobDAOImpl implements JobDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public JobDAOImpl(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;

	}

	@Transactional
	public boolean save(Job job) {
		try {
			System.out.println("save job in impl...");
			sessionFactory.getCurrentSession().save(job);
			return true;
		} catch (Exception e) {
			System.out.println("not saved job in impl...");
			e.printStackTrace();
			return false;
		}

	}

	@Transactional
	public boolean save(JobApplied jobApplied) {
		try {
			System.out.println("save jobApplied in impl....");
			sessionFactory.getCurrentSession().save(jobApplied);
			return true;
		} catch (Exception e) {

			System.out.println("not saved jobApplied in impl...");
			e.printStackTrace();
			return false;
		}

	}

	@Transactional
	public boolean update(Job job) {
		try {
			System.out.println("update jobApplied in impl....");
			sessionFactory.getCurrentSession().update(job);
			return true;
		} catch (Exception e) {
			System.out.println("update jobApplied in impl....");
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean update(JobApplied jobApplied) {
		try {
			sessionFactory.getCurrentSession().update(jobApplied);
			System.out.println("update jobApplied in impl....");
			return true;
		} catch (Exception e) {
			System.out.println("update jobApplied in impl....");
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public Job getJobDetails(String id) {
		String hql = "from Job where jobid='" + id + "'";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		List<Job> list = (List<Job>) query.list();

		if (list != null && !list.isEmpty()) {
			System.out.println("all job retrieved in  job Impl...");
			return list.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	public List<Job> getAllAvailableJobs() {

		System.out.println("getAll Available jobs in impl....");
		String hql = /* "from Job"; */"from Job where status='" + "v'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		return query.list();
	}

	@Transactional
	public List<Job> list() {
		String hql = " from Job";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Transactional
	public List<Job> getMyAppliedJobs(String userId) {

		String hql = "from Job where user_id='" + userId + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Transactional
	public JobApplied getJobApplied(String userId, String jobId) {
		String hql = " from JobApplied where userid='" + userId + "'and jobid='" + jobId + "'";
		 
		return (JobApplied) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();

	}

	@Transactional
	public JobApplied getJobApplied(String jobId) {
		String hql = "from JobApplied where job_id=" + jobId;

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<JobApplied> list = (List<JobApplied>) query.list();

		if (list != null && !list.isEmpty()) {
			System.out.println("job retrieved in jobImpl..");
			return list.get(0);
		} else {
			return null;
		}

	}

	public boolean delete(Job job) {
		// TODO Auto-generated method stub
		return false;
	}

	private int getMaxId() {
		int maxId = 100;
		System.out.println("starting of the getMaxId in impl...");
		try {
			String hql = "select max(jobid) from Job";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			maxId = (Integer) query.uniqueResult();
		} catch (HibernateException e) {

			System.out.println("It seems this is 1st record,setting initial id as 100");
			maxId = 100;
			e.printStackTrace();
		}
		System.out.println("max id:" + maxId);
		return maxId + 1;

	}

}
