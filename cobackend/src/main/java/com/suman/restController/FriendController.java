package com.suman.restController;

import java.util.ArrayList;
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

import com.suman.dao.FriendDAO;
import com.suman.dao.UserDAO;
import com.suman.model.Friend;
import com.suman.model.User;




@RestController
public class FriendController {
	@Autowired
	Friend friend;

	@Autowired
	FriendDAO friendDAO;
	
	@Autowired
	User user;

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	HttpSession session;
	
	/*GET MY FRIENDS................*/
	
	
	@RequestMapping(value = "/myFriends", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getAllMyFriends() {
		System.out.println("calling method listAllFriends in friend contrller.java");
		
		String loggedInUserId=(String) session.getAttribute("loggedInUserID");
		List<Friend> myfrienda = new ArrayList<Friend>();
		if (loggedInUserId==null) {
			
			friend.setErrorCode("404");
			friend.setErrorMessage("pls login to know ur friends..");
			myfrienda.add(friend);
			return new ResponseEntity<List<Friend>>(myfrienda,HttpStatus.OK);  //doesn't enter into else directly return's from mthd
		}
		
		System.out.println("getting friends of :"+loggedInUserId);
		myfrienda =friendDAO.viewMyFriends(loggedInUserId);
		if(myfrienda.isEmpty())
		{
			System.out.println("friends does not exists for the user "+loggedInUserId);
			friend.setErrorCode("404");
			friend.setErrorMessage("u do not hv friends:"+loggedInUserId);
			myfrienda.add(friend);
		}
		System.out.println("send the friend list ");
		
		return new ResponseEntity<List<Friend>>(myfrienda, HttpStatus.OK);
	}

	
	private boolean isUserExists(String id){
		if(userDAO.get(id)==null)
			return false;
		else 
			return true;
	}
	
	/*ADD FRIENDS................*/

	@RequestMapping(value = "/addFriend/{friendId}", method = RequestMethod.POST)
	public ResponseEntity<Friend> sendFriendReq(@PathVariable("friendId") String friendId) {
		
		String loggedInUserId = (String) session.getAttribute("loggedInUserID");
		friend.setUser_id(loggedInUserId);
		friend.setFriend_id(friendId);
		friend.setStatus('N');  // n-new registered
		friend.setIs_Online('N');
		
		// already sent friend req.
		//check if user exist in user table
		if(isUserExists(friendId)==false)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("no user exists with user id :"+friendId);
			
		}
		else
		if (friendDAO.get(loggedInUserId,friendId)!=null) {
			friend.setErrorCode("404");
			friend.setErrorMessage("u already sent the friend req....");
			
		} else {
			friendDAO.save(friend);
			
			friend.setErrorCode("200");
			friend.setErrorMessage("friend req successfully sent ..."+friendId);
			System.out.println("friend req successfully sent ..."+friendId);
			

		}
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/friend/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> updateFriend(@PathVariable("id") String friend_id, @RequestBody Friend friend) {
		System.out.println("calling method updateFriend :" + friend.getFriend_id());
		if (friendDAO.viewMyFriends(friend_id) == null) {
			System.out.println("friend does not exists with id:" + friend.getFriend_id());
			friend = new Friend();
			friend.setErrorMessage("friend does not exists with id:" + friend.getFriend_id());
			return new ResponseEntity<Friend>(friend, HttpStatus.NOT_FOUND);
		}
		friendDAO.update(friend);
		System.out.println("friend updated successfully...in friend controller.java");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}


	

	
	@RequestMapping(value = "unFriend/{friendID}", method = RequestMethod.GET)
	public ResponseEntity<Friend> unFriend(@PathVariable("friendID") String friendID) {
		System.out.println("calling method unFriend ");
		updateRequest(friendID, 'U');
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "rejectFriend/{friendID}", method = RequestMethod.GET)
	public ResponseEntity<Friend> rejectFriend(@PathVariable("friendID") String friendID) {
		System.out.println("calling method rejectFriend ");
		updateRequest(friendID, 'R');
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/acceptFriend/{friendID}", method = RequestMethod.GET)
	public ResponseEntity<Friend> acceptFriendRequest(@PathVariable("friendID") String friendID) {
		System.out.println("calling method acceptFriend ");
		updateRequest(friendID, 'A');
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMyFriendRequests/", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriendRequests() {
		System.out.println("calling method getMyFriendRequests ");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		
		List<Friend> myfriendreq = friendDAO.getNewFriendReq(loggedInUserID);
		
		
		return new ResponseEntity<List<Friend>>(myfriendreq, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/FriendReqSendByMe/", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getFriendRequestSendByMe() {
		System.out.println("calling method getFriendReqSentByMe ");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		
		List<Friend> reqsent = friendDAO.getFriendReqSentByMe(loggedInUserID);
		
		
		return new ResponseEntity<List<Friend>>(reqsent, HttpStatus.OK);
	}
	
	
	private Friend updateRequest(String friendID, char status) {
		System.out.println("common mthd update req in friend controller .java");
		
	
		
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		if( (status=='A') || (status=='R'))
			friend= friendDAO.get(friendID, loggedInUserID);
		else
			friend= friendDAO.get(loggedInUserID, friendID);
		
		friend.setStatus(status);
		
		friendDAO.update(friend);
		friend.setErrorCode("200");
		friend.setErrorMessage(" Req. from "+ friend.getUser_id() +" to "+ friend.getFriend_id() +"has updated to "+ status);
		
		System.out.println("loggedInUserId::::"+loggedInUserID+"friend id::::"+friendID+"status:::"+status);
		
		return friend;
	}

	

}
