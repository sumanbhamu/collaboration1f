'use strict';

app.service('RequestService',['$http','$q','$rootScope',function($http,$q,$rootScope){
	
	console.log("RequestService..")
	
	var BASE_URL='http://localhost:7188/cobackend/'
		return{
		
	
		fetchAllUsers:function(){
			return $http.get(BASE_URL+'/users')
			.then(
					 //success handler
					function(response){
						console.log('Calling user in user service....... ')
						return response.data;
					},//failure handler
					function(errResponse){
						console.error('Error while fetching UserDetails in reqService.js');
						return $q.reject(errResponse);
					}				
	);
},
/*SEND FRIEND REQUEST.......................................*/
sendFriendRequest:function(friendID){
	return $http.post(BASE_URL+'/addFriend/'+friendID)
	.then(
			function(response){
				return response.data;
			},
			function(errResponse){
				console.error('Error while sending Friend request....in requestService.js');
				return $q.reject(errResponse);
			}				
);
},



	}
	}
	]
	);