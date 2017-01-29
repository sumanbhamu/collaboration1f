package com.suman.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="c_forum")
@Component
public class Forum extends BaseDomain{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int forum_id;
	private String user_id;
	
	private String user_name;
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	private String created_date;
	
	private String forum_data;
	private String topic;

	public String gettopic() {
		return topic;
	}

	public void settopic(String topic) {
		this.topic = topic;
	}

	public int getForum_id() {
		return forum_id;
	}

	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getForum_data() {
		return forum_data;
	}

	public void setForum_data(String forum_data) {
		this.forum_data = forum_data;
	}
	
	
	
	
	
	
	
}
