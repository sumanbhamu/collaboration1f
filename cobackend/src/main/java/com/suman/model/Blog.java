package com.suman.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import org.springframework.stereotype.Component;


@Entity
@Table(name="c_blog")
@Component
public class Blog extends BaseDomain{
	
    @Id
	
	private String blog_id;
	private String title;
	private String user_id;  /* posted-by*/

	private Date created_date;
	private String blog_data;   /* blog_name   */

	private String description;
	
	private char status;   /* status-new  */
	
	private int likes;   /* likes*/
	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getBlog_id() {
		return blog_id;
	}

	public void setBlog_id(String blog_id) {
		this.blog_id = blog_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getBlog_data() {
		return blog_data;
	}

	public void setBlog_data(String blog_data) {
		this.blog_data = blog_data;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	


}
