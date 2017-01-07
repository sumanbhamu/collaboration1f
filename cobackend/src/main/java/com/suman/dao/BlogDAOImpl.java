package com.suman.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.suman.model.Blog;

@Repository(value = "BlogDAO")
public class BlogDAOImpl implements BlogDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public BlogDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean save(Blog blog) {
		try {
			sessionFactory.getCurrentSession().save(blog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Blog blog) {
		try {
			sessionFactory.getCurrentSession().update(blog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(Blog blog) {
		try {
			sessionFactory.getCurrentSession().delete(blog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Blog get(String blogID) {
		String hql = "from Blog where blog_id=" + "'" + blogID + "'";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Blog> list = (List<Blog>) query.list();

		if (list != null && !list.isEmpty()) {
			System.out.println("blog id.. retrieved from DAOImpl");
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<Blog> viewBlogs() {
		String hql = " from Blog";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	public List<Blog> viewMyBlogs(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addComment(Blog blogComment) {
		// TODO Auto-generated method stub

	}

	public List<Blog> viewComments(String blogId) {
		// TODO Auto-generated method stub
		return null;
	}

}
