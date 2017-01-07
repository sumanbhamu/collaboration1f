package com.suman.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.suman.model.Friend;

@Repository(value = "FriendDAO")
public class FriendDAOImpl implements FriendDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public FriendDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean save(Friend friend) {
		try {
			sessionFactory.getCurrentSession().save(friend);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean update(Friend friend) {
		try {
			sessionFactory.getCurrentSession().update(friend);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(String userID, String friendID) {
		try {
			sessionFactory.getCurrentSession().delete(friendID);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional

	public List<Friend> viewMyFriends(String userID) {

		/*
		 * Criteria
		 * at=sessionFactory.getCurrenntSession().createCriteria(Friend.class);
		 */
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public Friend getName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void setOnline(String userID) {
		String hql = "UPDATE Friend SET isOnline='Y' where friend_id=" + userID;

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();

	}

	@Transactional
	public void setOffline(String userID) {
		String hql = "UPDATE Friend SET isOnline='N' where friend_id=" + userID;

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();

	}

}
