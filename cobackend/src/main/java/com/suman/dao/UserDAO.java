package com.suman.dao;

import java.util.List;

import com.suman.model.User;

public interface UserDAO {

	
public boolean save(User user); 
	
	public User get(String userID);
	
	public User getName(String name);
	
	
	public boolean update(User user);
	
	public boolean delete(User user);
	
	public List<User> list();
	
	public User isValidUser(String userid, String password);
	
	
	public void setOnline(String userID);
	
	public void setOffline(String userID);

}
