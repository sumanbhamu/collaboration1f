package com.suman.restController;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.suman.dao.ForumDAO;
import com.suman.model.Forum;
import com.suman.model.User;


@RestController
public class ForumController {

//private static final Logger logger	= LoggerFactory.getLogger(ForumController.class);
	
	@Autowired
	ForumDAO forumDAO;
	
	@Autowired
	User user;
	
	@RequestMapping(value="/forums",method=RequestMethod.GET)
	public ResponseEntity<List<Forum>> listAllForums(){
		System.out.println("calling method listAllForums");
		List<Forum> forum=forumDAO.list();
		if(forum.isEmpty()){
			return new ResponseEntity<List<Forum>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Forum>>(forum,HttpStatus.OK);
	}

	@RequestMapping(value="/forum/",method=RequestMethod.POST)
	public ResponseEntity<Forum> createForum(@RequestBody Forum forum,HttpSession httpSession){
		System.out.println("calling method createForum" + forum.getForum_id());
		if(forumDAO.get(forum.getForum_id())==null){
			String looggedInUserID=(String) httpSession.getAttribute("loggedInUserID");
			Date dt=new java.util.Date();
			forum.setCreated_date(dt.toString());
			//user=(User) httpSession.getAttribute("loggedInUser");
			forum.setUser_name(looggedInUserID);
			forum.setUser_id(looggedInUserID);
			forumDAO.save(forum);			
		}
		System.out.println("forum already exists with id:" + forum.getForum_id());
		forum.setErrorMessage("forum already exists with id:" + forum.getForum_id());
		return new ResponseEntity<Forum>(forum,HttpStatus.OK);
			}
	
	@RequestMapping(value="/forum/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Forum> updateForum(@PathVariable("id") int forum_id,@RequestBody Forum forum){
		System.out.println("calling method updateForum" + forum.getForum_id());
		if(forumDAO.get(forum_id)==null){
			System.out.println("forum does not exists with id:" + forum.getForum_id());		
			forum=new Forum();
			forum.setErrorMessage("forum does not exists with id:" + forum.getForum_id());
			return new ResponseEntity<Forum> (forum,HttpStatus.NOT_FOUND);
		}
		forumDAO.update(forum);
		System.out.println("forum updated successfully");
		return new ResponseEntity<Forum> (forum,HttpStatus.OK);		
	}

	@RequestMapping(value="/forum/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Forum> deleteForum(@PathVariable("id") int forum_id){
		System.out.println("calling method deleteForum for forum id: " + forum_id);
		Forum forum=forumDAO.get(forum_id);
		if(forum==null){
			System.out.println("forum does not exists with id:" + forum_id);
			forum=new Forum();
			forum.setErrorMessage("forum does not exists with id:" + forum_id);
			return new ResponseEntity<Forum> (forum,HttpStatus.NOT_FOUND);	
		}
		forumDAO.delete(forum_id);
		System.out.println("forum deleted successfully");
		return new ResponseEntity<Forum> (forum,HttpStatus.OK);		
	}
	
	@RequestMapping(value="/forum/{id}",method=RequestMethod.GET)
	public ResponseEntity<Forum> getForum(@PathVariable("id") int forum_id){
		System.out.println("calling method getForum for forum id: " + forum_id);
		Forum forum=forumDAO.get(forum_id);
		if(forum==null){
			System.out.println("forum does not exists with id:" + forum_id);
			forum=new Forum();
			forum.setErrorMessage("forum does not exists with id:" + forum_id);
			return new ResponseEntity<Forum> (forum,HttpStatus.NOT_FOUND);
		}
		System.out.println("forum exists with id:" + forum_id);
		return new ResponseEntity<Forum> (forum,HttpStatus.OK);
	}


}




