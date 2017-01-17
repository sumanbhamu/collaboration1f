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

	
	private Integer getMaxId(){
		System.out.println("**in maxId **in fr DAO impl**");
		
		String hql="select max(id) from Friend";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		System.out.println("hql::::"+hql);
		Integer maxId;
		try {
			maxId=(Integer) query.uniqueResult();
			
			System.out.println("max id value is in friend dao::"+maxId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("setting as 100 value to  id  in friend dao impl");
			return 0;
			
		}
		return maxId;
	}
	@Transactional
	public boolean save(Friend friend) {
		try {
			friend.setId(getMaxId()+1);
			sessionFactory.getCurrentSession().save(friend);
			System.out.println("saving...in frienndDAO impl**");
			return true;
		} catch (Exception e) {
			System.out.println("unable to save...in frienndDAO impl**");
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean update(Friend friend) {
		try {
			sessionFactory.getCurrentSession().update(friend);
			System.out.println("update...in frienndDAO impl**");
			return true;
		} catch (Exception e) {
			System.out.println("unable to update...in frienndDAO impl**");
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(String userID, String friendID) {
		try {
			Friend friend=new Friend();
			friend.setFriend_id(friendID);
			friend.setUser_id(userID);
			
			sessionFactory.getCurrentSession().delete(friend);
			System.out.println("delete...in frienndDAO impl**");
			return true;
		} catch (Exception e) {
			System.out.println("unable to delete...in frienndDAO impl**");
			e.printStackTrace();
			return false;
		}
	}

	@Transactional

	public List<Friend> viewMyFriends(String userId/*,String friendId*/) {

		String hql = "select friend_id from Friend where user_id='"+userId+"' and status='"+"A'" ;
		
		String hql1 ="select user_id from Friend where friend_id='"+userId+"' and status='"+"A'" ;
		
		System.out.println("hql : : "+hql);
		System.out.println("hql1 : : "+hql1);
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		Query query1 = sessionFactory.getCurrentSession().createQuery(hql1);
		
		List<Friend> list=(List<Friend>)query.list();
		List<Friend> list1=(List<Friend>)query1.list();
		
		list.addAll(list1);
		
		System.out.println("list..in friend dao impl::::"+list);
		return list;
	}

	@Transactional
	public Friend get(String friendId) {
		
		return null;
	}

	@Transactional
	public void setOnline(String userId) {
		String hql = "UPDATE Friend SET isOnline='Y' where friend_id=" + userId;

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();

	}

	@Transactional
	public void setOffline(String userId) {
		String hql = "UPDATE Friend SET isOnline='N' where friend_id=" + userId;

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();

	}

	@Transactional
	public List<Friend> getNewFriendReq(String friendId) {
		String hql = "select friend_id from Friend where friend_id='" +  friendId + "' and status = '" + "N'";
	
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		List<Friend> list = (List<Friend>) query.list();
		
		System.out.println("list of new friend req in friend controller******"+list);
        return list;
	}
	
	@Transactional
	public List<Friend> getFriendReqSentByMe(String userId) {
		String hql = "select friend_id from Friend where user_id='" +  userId + "' and status = '" + "N'";
	
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		List<Friend> list = (List<Friend>) query.list();
		
		System.out.println("list of new friend req in friend controller******"+list);
        return list;
	}
	
	
	
	@Transactional
	public Friend get(String userID, String friendID) {
		String hql="from Friend where user_id='" + userID + "'and friend_id='" +  friendID+"'" ;
	System.out.println("hql**** " + hql);
	Query query=sessionFactory.getCurrentSession().createQuery(hql);
	List<Friend> list = (List<Friend>) query.list();

	if (list != null && !list.isEmpty()) {
		System.out.println(" retrieve friends  in friend DAOImpl****");
		return list.get(0);
	} else {
		return null;
	}
}


}
