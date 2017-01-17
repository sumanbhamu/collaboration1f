package com.suman.dao;

import java.util.List;

import com.suman.model.Friend;

public interface FriendDAO {

	

	public boolean save(Friend friend); 
	public boolean update(Friend friend);
	public boolean delete(String userID,String friendId);
	
	public List<Friend> viewMyFriends(String userId); /*get my friends*/
	
	
	public List<Friend> getNewFriendReq(String userId); /*listing new friend requests*/
	
	public Friend get(String friendId);        /* get friends*/
	
	public void setOnline(String userId);
	
	public void setOffline(String userId);
	
	
	public Friend get(String userID, String friendID);
	
	public List<Friend> getFriendReqSentByMe(String userId) ;
}
