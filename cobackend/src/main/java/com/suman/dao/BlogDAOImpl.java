package com.suman.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.suman.model.Blog;
import com.suman.model.BlogComment;

@Repository(value = "BlogDAO")
public class BlogDAOImpl implements BlogDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public BlogDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean save(Blog blog) {
		try {
			sessionFactory.getCurrentSession().save(blog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean update(Blog blog) {
		try {
			sessionFactory.getCurrentSession().update(blog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(Blog blog) {
		try {
			sessionFactory.getCurrentSession().delete(blog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
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

	@Transactional
	public List<Blog> viewBlogs() {
		String hql = " from Blog";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Transactional
	public  boolean addComment(BlogComment blogComment) {
		try {
			sessionFactory.getCurrentSession().save(blogComment);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public List<BlogComment> viewMyBlogs(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<BlogComment> viewComments(String blogId) {
		
		String hql = " from BlogComment";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();

	
	}

}
