package com.suman.dao;

import java.util.List;

import com.suman.model.Forum;

public interface ForumDAO {

    public boolean save(Forum forum); 
	
	public boolean update(Forum forum);
	
	public boolean delete(String forumID);
	
	public Forum get(String forumID);
	
	
	
	public List<Forum> list();

}
