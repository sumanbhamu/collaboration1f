package com.suman.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.suman.model.User;

@Repository(value = "UserDAO")
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public UserDAOImpl(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;

	}

	@Transactional
	public boolean save(User user) {
		try {
			sessionFactory.getCurrentSession().save(user);
			System.out.println("save in user dao impl......");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean update(User user) {
		try {
			sessionFactory.getCurrentSession().update(user);
			System.out.println("update in user dao impl......");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(User user) {
		try {
			sessionFactory.getCurrentSession().delete(user);
			System.out.println("delete in user dao impl......");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public User get(String userID) {
		User user = (User) sessionFactory.getCurrentSession().get(User.class, userID);
		System.out.println("get in user dao impl......");
		return user;
	}

	@Transactional
	public User getName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<User> list() {
		String hql = "from User";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		System.out.println("listingg users in impl......");
		return query.list();
	}

	@Transactional /* ..Authentication... */
	public User isValidUser(String emailid, String password) {
		String hql = "from User where emailid='" + emailid + "'and password='" + password + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		User user = (User) query.uniqueResult();
		System.out.println("is validate in user dao impl......");
		return user;
	}

	@Transactional
	public void setOnline(String userID) {

		String hql = " UPDATE User	SET isOnline = 'Y' where userid='" + userID + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		System.out.println("set online in user dao impl......");
		query.executeUpdate();

	}

	@Transactional
	public void setOffline(String userID) {
		String hql = " UPDATE User	SET isOnline = 'N' where userid='" + userID + "'";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		System.out.println("set offline in user dao impl......");
		query.executeUpdate();

	}

}
