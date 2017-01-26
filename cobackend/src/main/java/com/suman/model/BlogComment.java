package com.suman.model;

//import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="c_blog_comment")
@Component
public class BlogComment extends BaseDomain{
	@Id
	private String id;
	
	private String blog_id;
	private String user_id;
	private String blog_comment_data;  //comments
	private String bcomment_date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBlog_id() {
		return blog_id;
	}
	public void setBlog_id(String blog_id) {
		this.blog_id = blog_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getBlog_comment_data() {
		return blog_comment_data;
	}
	public void setBlog_comment_data(String blog_comment_data) {
		this.blog_comment_data = blog_comment_data;
	}
	public String getBcomment_date() {
		return bcomment_date;
	}
	public void setBcomment_date(String bcomment_date) {
		this.bcomment_date = bcomment_date;
	}
	
	
	
}
