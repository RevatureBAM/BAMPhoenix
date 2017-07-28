package com.bam.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.beans.Users;
import com.bam.service.UsersService;


@RestController
@RequestMapping(value="/Users/")
public class UserController {
	@Autowired
	UsersService userService;
	
	@RequestMapping(value="All.do", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getAllUsers(){
		return userService.findAllUsers();
	}
	
	@RequestMapping(value="InBatch.do", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getUsersInBatch(HttpServletRequest request) {
		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter("batchId") );
		
		//Retrieve and return users in a batch from the database
		return userService.findUsersInBatch(batchId);
	}
	
	@RequestMapping(value="Update.do", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public void updateUser(@RequestBody String jsonObject, HttpSession session) {
		Users currentUser = null;
		System.out.println("jsonObject: " + jsonObject);
		try {
			currentUser = new ObjectMapper().readValue(jsonObject, Users.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		userService.addUser(currentUser);
		
		//Retrieve and return users in a batch from the database
	}
}