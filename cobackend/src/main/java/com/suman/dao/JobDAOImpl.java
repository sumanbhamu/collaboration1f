package com.suman.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.suman.model.Job;

public class JobDAOImpl implements JobDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public JobDAOImpl(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;

	}
	
	@Transactional
	public boolean save(Job job) {
		try {
			sessionFactory.getCurrentSession().save(job);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	@Transactional
	public boolean update(Job job) {
		try {
			sessionFactory.getCurrentSession().update(job);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public Job get(String jobID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Job> list() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean delete(Job job) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
