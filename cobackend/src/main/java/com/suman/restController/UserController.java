package com.suman.restController;

import java.util.List;

import javax.servlet.http.HttpSession;package com.suman.restController;

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
import com.suman.model.User;

@RestController
public class UserController {

	@Autowired
	HttpSession httpSession;

	@Autowired
	User user;

	@Autowired
	FriendDAO friendDAO;

	@Autowired
	UserDAO userDAO;

	/* http://localhost:8088/cobackend/userL----userList */
	@RequestMapping("/users")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> users = userDAO.list();

		if (users.isEmpty()) {
			user.setErrorCode("404");
			user.setErrorMessage("No users r available");
			// adding this msg
			users.add(user);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	/* http://localhost:8088/cobackend/userL/uid---to get particular user */

	@RequestMapping("/users/{uid}")
	public ResponseEntity<User> getByUserId(@PathVariable("uid") String userId) {
		user = userDAO.get(userId);
		if (user == null) {
			user = new User();// to avoid NLP-EX
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist with id " + userId);

		}
		user.setErrorCode("200");
		user.setErrorMessage("User exists with id " + userId);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* http://localhost:8088/cobackend/userL/uid/password---authenticate user */
	@RequestMapping(value = "/authenticate/", method = RequestMethod.POST)
	public ResponseEntity<User> autheticate(@RequestBody User user, HttpSession session) {
		user = userDAO.isValidUser(user.getUserid(), user.getPassword());
		if (user == null) // if the credentials r wrong
		{
			user = new User(); // to avoid NLP-EX
			user.setErrorCode("404");
			user.setErrorMessage("invalid credentials.. pls try again!!");

		} else // valid credentials
		{

			user.setErrorCode("200");
			user.setErrorMessage("successfully logged in...");

			user.setIsOnline('Y');
			session.setAttribute("loggedInUser", user);

			session.setAttribute("loggedInUserID", user.getUserid());
			friendDAO.setOnline(user.getUserid());
			userDAO.setOnline(user.getUserid());
			// store the id in session
			// httpSession.setAttribute("loggedInUserId",user.getUserid());
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* register */
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<User> register(@RequestBody User user) {
		/*
		 *........... if(userDAO.get(user.getUserid())!=null){ user.setErrorCode("404");
		 * user.setErrorMessage("User already exists with the id :"+user.
		 * getUserid());
		 * 
		 * } else{ userDAO.save(user); user.setErrorCode("200");
		 * user.setErrorMessage("Thakyou for the registration...."); } return
		 * new ResponseEntity<User>(user,HttpStatus.OK);...........
		 */

		if (userDAO.get(user.getUserid()) == null) {

			user.setIsOnline('N');
			user.setStatus("N");
			if (userDAO.save(user) == true) {
				user.setErrorCode("200");
				user.setErrorMessage(
						"Thank you for registration.You have successfully registered as " + user.getRole());
			} else {
				user.setErrorCode("404");
				user.setErrorMessage("Could not complete the operatin please contact Admin");
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);

		}
		user.setErrorCode("404");
		user.setErrorMessage("User already exist with id : " + user.getUserid());
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	/* Update */
	@RequestMapping(value = "/update/", method = RequestMethod.PUT)
	public ResponseEntity<User> update(@RequestBody User user) {
		if (userDAO.get(user.getUserid()) == null) {
			user.setErrorCode("404");
			user.setErrorMessage("Update is not successfull..pls try after some time...");

		} else {
			userDAO.update(user);
			user.setErrorCode("200");
			user.setErrorMessage("Successfully updated......");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* makeAdmin */
	@RequestMapping(value = "/makeAdmin/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> makeAdmin(@PathVariable("id") String userId) {

		user = userDAO.get(userId);

		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Employee does not exists..");
			return new ResponseEntity<User>(user, HttpStatus.OK);

		}
		if (user.getRole() != "Employee") {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("We cannot make this user as admin.." + userId);
			return new ResponseEntity<User>(user, HttpStatus.OK);

		}
		user.setRole("Admin");
		userDAO.update(user);
		user.setErrorCode("200");
		user.setErrorMessage("Employee role updated as admin successfully:" + user.getUsername() + " " + userId);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* Accept/uid */
	@RequestMapping(value = "/accept/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> accept(@PathVariable("id") String id) {

		user = updateStatus(id, "Accept", "");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* Reject */
	@RequestMapping(value = "/reject/{id}/{reasonn}", method = RequestMethod.GET)
	public ResponseEntity<User> reject(@PathVariable("id") String id, @PathVariable("reasonn") String reason) {

		user = updateStatus(id, "Reject", reason);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* this mthd is used in accept and reject */
	private User updateStatus(String id, String status, String reason) {

		user = userDAO.get(id);
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("could not update the status to :" + status);

		} else {
			user.setStatus(status);
			user.setReason(reason);
			userDAO.update(user);
			user.setErrorCode("200");
			user.setErrorMessage("status is updated successfully..");

		}
		return user;
	}
	/* ........................................ */

	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)

	public ResponseEntity<User> logout(HttpSession session) {

		String loggedInUserID = (String) session.getAttribute("loggedInUserID");

		friendDAO.setOffline(loggedInUserID);

		userDAO.setOffline(loggedInUserID);

		session.invalidate();

		user.setErrorCode("200");

		user.setErrorMessage("You have successfully logged");

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* ///////////////////////////////// */

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)

	public ResponseEntity<User> myProfile(HttpSession session) {
		System.out.println("->->calling method myProfile");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		User user = userDAO.get(loggedInUserID);
		if (user == null) {
			System.out.println("->->->-> User does not exist wiht id" + loggedInUserID);
			user = new User(); // It does not mean that we are inserting new
								// user
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist");
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}

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
import com.suman.model.User;

@RestController
public class UserController {

	@Autowired
	HttpSession httpSession;

	@Autowired
	User user;

	@Autowired
	FriendDAO friendDAO;

	@Autowired
	UserDAO userDAO;

	/* http://localhost:8088/cobackend/userL----userList */
	@RequestMapping("/userL")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> users = userDAO.list();

		if (users.isEmpty()) {
			user.setErrorCode("404");
			user.setErrorMessage("No users r available");
			// adding this msg
			users.add(user);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	/* http://localhost:8088/cobackend/userL/uid---to get particular user */

	@RequestMapping("/userL/{uid}")
	public ResponseEntity<User> getByUserId(@PathVariable("uid") String userId) {
		user = userDAO.get(userId);
		if (user == null) {
			user = new User();// to avoid NLP-EX
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist with id " + userId);

		}
		user.setErrorCode("200");
		user.setErrorMessage("User exists with id " + userId);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* http://localhost:8088/cobackend/userL/uid/password---authenticate user */
	@RequestMapping(value = "/authenticate/", method = RequestMethod.POST)
	public ResponseEntity<User> autheticate(@RequestBody User user, HttpSession session) {
		user = userDAO.isValidUser(user.getUserid(), user.getPassword());
		if (user == null) // if the credentials r wrong
		{
			user = new User(); // to avoid NLP-EX
			user.setErrorCode("404");
			user.setErrorMessage("invalid credentials.. pls try again!!");

		} else // valid credentials
		{

			user.setErrorCode("200");
			user.setErrorMessage("successfully logged in...");

			user.setIsOnline('Y');
			session.setAttribute("loggedInUser", user);

			session.setAttribute("loggedInUserID", user.getUserid());
			friendDAO.setOnline(user.getUserid());
			userDAO.setOnline(user.getUserid());
			// store the id in session
			// httpSession.setAttribute("loggedInUserId",user.getUserid());
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* register */
	@RequestMapping(value = "/register/", method = RequestMethod.POST)
	public ResponseEntity<User> register(@RequestBody User user) {
		/*
		 * if(userDAO.get(user.getUserid())!=null){ user.setErrorCode("404");
		 * user.setErrorMessage("User already exists with the id :"+user.
		 * getUserid());
		 * 
		 * } else{ userDAO.save(user); user.setErrorCode("200");
		 * user.setErrorMessage("Thakyou for the registration...."); } return
		 * new ResponseEntity<User>(user,HttpStatus.OK);
		 */

		if (userDAO.get(user.getUserid()) == null) {

			user.setIsOnline('N');
			user.setStatus("N");
			if (userDAO.save(user) == true) {
				user.setErrorCode("200");
				user.setErrorMessage(
						"Thank you for registration.You have successfully registered as " + user.getRole());
			} else {
				user.setErrorCode("404");
				user.setErrorMessage("Could not complete the operatin please contact Admin");
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);

		}
		user.setErrorCode("404");
		user.setErrorMessage("User already exist with id : " + user.getUserid());
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	/* Update */
	@RequestMapping(value = "/update/", method = RequestMethod.PUT)
	public ResponseEntity<User> update(@RequestBody User user) {
		if (userDAO.get(user.getUserid()) == null) {
			user.setErrorCode("404");
			user.setErrorMessage("Update is not successfull..pls try after some time...");

		} else {
			userDAO.update(user);
			user.setErrorCode("200");
			user.setErrorMessage("Successfully updated......");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* makeAdmin */
	@RequestMapping(value = "/makeAdmin/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> makeAdmin(@PathVariable("id") String userId) {

		user = userDAO.get(userId);

		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Employee does not exists..");
			return new ResponseEntity<User>(user, HttpStatus.OK);

		}
		if (user.getRole() != "Employee") {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("We cannot make this user as admin.." + userId);
			return new ResponseEntity<User>(user, HttpStatus.OK);

		}
		user.setRole("Admin");
		userDAO.update(user);
		user.setErrorCode("200");
		user.setErrorMessage("Employee role updated as admin successfully:" + user.getUsername() + " " + userId);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* Accept/uid */
	@RequestMapping(value = "/accept/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> accept(@PathVariable("id") String id) {

		user = updateStatus(id, "Accept", "");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* Reject */
	@RequestMapping(value = "/reject/{id}/{reasonn}", method = RequestMethod.GET)
	public ResponseEntity<User> reject(@PathVariable("id") String id, @PathVariable("reasonn") String reason) {

		user = updateStatus(id, "Reject", reason);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* this mthd is used in accept and reject */
	private User updateStatus(String id, String status, String reason) {

		user = userDAO.get(id);
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("could not update the status to :" + status);

		} else {
			user.setStatus(status);
			user.setReason(reason);
			userDAO.update(user);
			user.setErrorCode("200");
			user.setErrorMessage("status is updated successfully..");

		}
		return user;
	}
	/* ........................................ */

	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)

	public ResponseEntity<User> logout(HttpSession session) {

		String loggedInUserID = (String) session.getAttribute("loggedInUserID");

		friendDAO.setOffline(loggedInUserID);

		userDAO.setOffline(loggedInUserID);

		session.invalidate();

		user.setErrorCode("200");

		user.setErrorMessage("You have successfully logged");

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/* ///////////////////////////////// */

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)

	public ResponseEntity<User> myProfile(HttpSession session) {
		System.out.println("->->calling method myProfile");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		User user = userDAO.get(loggedInUserID);
		if (user == null) {
			System.out.println("->->->-> User does not exist wiht id" + loggedInUserID);
			user = new User(); // It does not mean that we are inserting new
								// user
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist");
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
