package com.suman.dao;

import java.util.List;

import com.suman.model.Blog;

public interface BlogDAO {

public boolean save(Blog blog); 
	
	public boolean update(Blog blog);
	
	public boolean delete(Blog blog);
	
	public Blog get(String blogID);
	
	/*public Blog getName(String name);*/
	
    public List<Blog> viewBlogs();
    
    /*related to comment*/
    List<Blog> viewMyBlogs(String userId);  /*posted by*/
    
    void addComment(Blog blogComment);
    List<Blog> viewComments(String blogId);
}
