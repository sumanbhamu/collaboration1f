package com.suman.controller;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.suman.model.Message;
import com.suman.model.OutputMessage;

@Controller
public class ChatForumController {
	
	@MessageMapping("/chat_forum")        //send message
	@SendTo("/topic/message")       //Receive message
	public OutputMessage sendMessage(Message message){
		System.out.println("Calling the method sendMessage");
		System.out.println("Message: "+message.getMessage());
		
		
		return new OutputMessage(message,new Date());  //appending current date
		
}

}
