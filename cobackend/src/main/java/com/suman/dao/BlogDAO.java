package com.suman.dao;

import java.util.List;

import com.suman.model.Blog;
import com.suman.model.BlogComment;

public interface BlogDAO {

public boolean save(Blog blog); 
	
	public boolean update(Blog blog);
	
	public boolean delete(Blog blog);
	
	public Blog get(String blogID);
	
	
	
    public List<Blog> viewBlogs();   // list of blogs
    
    /*related to comment*/
    
    public boolean addComment(BlogComment blogComment); //to add comments
    
    
    List<BlogComment> viewMyBlogs(String userId);  /*posted by -list individual blog*/
    
    
    List<BlogComment> viewComments(String blogId);   // list of comments
}
