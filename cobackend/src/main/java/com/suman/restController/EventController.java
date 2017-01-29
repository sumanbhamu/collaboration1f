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

import com.suman.dao.EventDAO;
import com.suman.model.Event;
import com.suman.model.User;

@RestController
public class EventController {
	
	@Autowired
	EventDAO eventDAO;
	
	@Autowired
	User user;
	
	@RequestMapping(value="/events",method=RequestMethod.GET)
	public ResponseEntity<List<Event>> listAllEvents(){
		System.out.println("calling method listAllEvents");
		List<Event> event=eventDAO.list();
		if(event.isEmpty()){
			return new ResponseEntity<List<Event>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Event>>(event,HttpStatus.OK);
	}

	@RequestMapping(value="/event/",method=RequestMethod.POST)
	public ResponseEntity<Event> createEvent(@RequestBody Event event,HttpSession httpSession){
		
		System.out.println("calling method createEvent" + event.getEvent_id());
		if(eventDAO.get(event.getEvent_id())==null){
			Date dt=new java.util.Date();
			event.setHeld_date(dt.toString());
			String loggeddInUserId = (String) httpSession.getAttribute("loggedInUserID");
			
			
			event.setUser_name(loggeddInUserId);
			eventDAO.save(event);			
		}
		System.out.println("event already exists with id:" + event.getEvent_id());
		event.setErrorMessage("event already exists with id:" + event.getEvent_id());
		return new ResponseEntity<Event>(event,HttpStatus.OK);
			}
	
	@RequestMapping(value="/event/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Event> updateEvent(@PathVariable("id") int event_id,@RequestBody Event event){
		System.out.println("calling method updateEvent" + event.getEvent_id());
		if(eventDAO.get(event_id)==null){
			System.out.println("event does not exists with id:" + event.getEvent_id());		
			event=new Event();
			event.setErrorMessage("event does not exists with id:" + event.getEvent_id());
			return new ResponseEntity<Event> (event,HttpStatus.NOT_FOUND);
		}
		eventDAO.update(event);
		System.out.println("event updated successfully");
		return new ResponseEntity<Event> (event,HttpStatus.OK);		
	}

	@RequestMapping(value="/event/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Event> deleteEvent(@PathVariable("id") int event_id){
		System.out.println("calling method deleteEvent for event id: " + event_id);
		Event event=eventDAO.get(event_id);
		if(event==null){
			System.out.println("event does not exists with id:" + event_id);
			event=new Event();
			event.setErrorMessage("event does not exists with id:" + event_id);
			return new ResponseEntity<Event> (event,HttpStatus.NOT_FOUND);	
		}
		eventDAO.delete(event);
		System.out.println("event deleted successfully");
		return new ResponseEntity<Event> (event,HttpStatus.OK);		
	}
	
	@RequestMapping(value="/event/{id}",method=RequestMethod.GET)
	public ResponseEntity<Event> getEvent(@PathVariable("id") int event_id){
		System.out.println("calling method getEvent for event id: " + event_id);
		Event event=eventDAO.get(event_id);
		if(event==null){
			System.out.println("event does not exists with id:" + event_id);
			event=new Event();
			event.setErrorMessage("event does not exists with id:" + event_id);
			return new ResponseEntity<Event> (event,HttpStatus.NOT_FOUND);
		}
		System.out.println("event exists with id:" + event_id);
		return new ResponseEntity<Event> (event,HttpStatus.OK);
	}


	

}
