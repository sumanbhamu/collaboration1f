package com.suman.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="c_forum")
@Component
public class Forum extends BaseDomain{

	@Id
	
	private String forum_id;
	private String user_id;
	private Date created_date;
	
	private String forum_data;

	public String getForum_id() {
		return forum_id;
	}

	public void setForum_id(String forum_id) {
		this.forum_id = forum_id;
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

	public String getForum_data() {
		return forum_data;
	}

	public void setForum_data(String forum_data) {
		this.forum_data = forum_data;
	}
	
	
	
	
	
	
	
}
