package com.suman.dao;

import java.util.List;

import com.suman.model.Friend;

public interface FriendDAO {

	
public boolean save(Friend friend); 
	
	public boolean update(Friend friend);
	
	public boolean delete(String userID,String friendID);
	
	public List<Friend> viewMyFriends(String userID); /*listing user name/id*/
	
	public Friend getName(String name);
	
	public void setOnline(String userID);
	
	public void setOffline(String userID);
}
