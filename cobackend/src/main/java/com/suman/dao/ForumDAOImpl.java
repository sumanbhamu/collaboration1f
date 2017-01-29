package com.suman.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.suman.model.Forum;

@Repository(value = "ForumDAO")
public class ForumDAOImpl implements ForumDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public ForumDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean save(Forum forum) {
		try {
			sessionFactory.getCurrentSession().save(forum);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean update(Forum forum) {
		try {
			sessionFactory.getCurrentSession().update(forum);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(int forumID) {
		try {
			sessionFactory.getCurrentSession().delete(forumID);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public Forum get(int forumID) {
		String hql = "from Forum where forum_id=" +  forumID ;

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		List<Forum> list = (List<Forum>) query.list();

		if (list != null && !list.isEmpty()) {
			System.out.println("forum retrieved from DAOImpl.....");
			return list.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	public List<Forum> list() {
		String hql = " from Forum";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

}
