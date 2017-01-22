
app.service("ChatForumService",function($q,$timeout){
	console.log('Chat forum Service...')
	var service={},listener=$q.defer(),socket={  // similar to int x,y
		client:null,
		stomp:null
	},messageIds=[];
	
	service.RECONNECT_TIMEOUT=30000;
	service.SOCKET_URL="http://localhost:7188/cobackend/chat_forum";    // from backend
	service.CHAT_TOPIC="/topic/message";
	service.CHAT_BROKER="/app/chat_forum";
	
	service.receive=function(){    //this mthd  will b called from chat forum controller
		console.log("receive")
		return listener.promise;
	};
	
	service.send=function(message){   //this mthd  will b called from chat forum controller
		console.log("send")
		var id=Math.floor(Math.random()*1000000);
		
		socket.stomp.send(service.CHAT_BROKER,{     //send(destination,{},body)
			priority:9
		},JSON.stringify({        //converts json to string
			message:message,
			id:id
		}));
		messageIds.push(id);
	};
	
	var reconnect=function(){
		console.log("reconnect")
		$timeout(function(){          //wrapper window $timeout([fn],[delay])
			initialize();
		},this.RECONNECT_TIMEOUT);
	};
	
	var getMessage=function(data){
		console.log("getMessage")
		var message=JSON.parse(data),out={};  //out json obj
		out.message=message.message;
		out.time=new Date(message.time);
		return out;
	};
	
	var startListener=function(){
		console.log("receive")
		socket.stomp.subscribe(service.CHAT_TOPIC,  //subscribe(destination,callback,{id:mysubid})- first need to register
				function(data){  
			listener.notify(getMessage(data.body));  //pop up
		});
	};
	
	var initialize=function(){
		console.log("initialize")
		socket.client=new SockJS(service.SOCKET_URL);
		socket.stomp=Stomp.over(socket.client);   //over-built in static mthd in JS
		socket.stomp.connect({},startListener); // connect(headers,connectcallback,errorcallback)
        socket.stomp.onclose=reconnect;
	};
	
	initialize();             //to call first time explicitly
	return service;
});