'use strict';

app.controller('RequestController', [
		'$scope',
		'RequestService',
		'$location',
		'$rootScope',
		function($scope, RequestService, $location, $rootScope) {
			console.log("RequestController...")
			var self = this;
			self.user = {
					userid : '',
					username : '',
					password : '',
					cpassword : '',
					phno : '',
					status : '',
					emailid : '',
					isOnline : '',
					role : '',
					errorCode : '',
					errorMessage : '',
			};
			self.users = [];
			
			self.friend={ 
					id : '',
					friend_id : '',
					/*friend_name:'',*/
					user_id : '',
					status : '',
					isOnline : '',
					errorCode:'',
					errorMessage:''
				};
			self.friends=[];
			
			
			/* FETCH ALL USERS LIST .............................................*/
			self.fetchAllUsers = function() {
				RequestService
						.fetchAllUsers()
						.then(
								function(d) {
									self.users = d;
									console.log("get all the users in RequestController.js....")
								},
								function(errResponse) {
									console
											.error('Error while fetching Users...in requestController.js');
								});
			};
			self.fetchAllUsers();
			
			/*SENDING FRIEND REQUEST...........................*/		
			self.sendFriendRequest=sendFriendRequest

			function sendFriendRequest(friendID)
			{
			console.log("sendFriendRequest:" + friendID)
			RequestService.sendFriendRequest(friendID)
			.then(
			function(d){
				self.friend=d;
				alert("Friend request sent")
			},function(errResponse){
				console.error('Error while sending Friend requests...in RequestController.js');
				
			});
			};

	
		} ]);