

'use strict'

app
		.controller(
				'FriendController',
				[
						'$scope',
						'FriendServic',
						'$location',
						'$rootScope',
						'$http',
						function($scope, FriendServic, $location, $rootScope,
						  $http) {




console.log("FriendController....")
	var self=this;
	self.friend={ // initialization
			id : '',
			friend_id : '',
			user_id : '',
			status : '',
			isOnline : '',
			errorCode:'',
			errorMessage:''
		};
	self.friends=[];
	
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

/*SENDING FRIEND REQUEST.............................*/		
self.sendFriendRequest=sendFriendRequest

function sendFriendRequest(friendID)
{
console.log("sendFriendRequest:" + friendID)
FriendServic.sendFriendRequest(friendID)
.then(
function(d){
	self.friend=d;
	alert("Friend request sent")
},function(errResponse){
	console.error('Error while sending Friend requests');
	
});
};

/*GET MY FRIENDS LIST.....................................*/
self.getMyFriends=function(){
	console.log("getting My Friends")
	FriendServic.getMyFriends()
	.then(
	function(d){
		self.friends=d;
		console.log("Got the friends list")
	},function(errResponse){
		console.error('Error while fetching Friend    ...controller.js');
		
	});
	};
	
self.updateFriendRequest=function(friend,id){
	FriendServic.updateFriendRequest(friend,id)
	.then(
	self.fetchAllFriends,
	function(errResponse){
		console.error('Error while updating Friend in friendcontroller.js');
	}
	);
};

self.deleteFriend=function(id){
	FriendServic.deleteFriend(id)
	.then(
	self.fetchAllFriends,
	function(errResponse){
		console.error('Error while deleting Friend in friend controller.js');
	}
	);
};


self.fetchAllUsers = function() {
	FriendServic
			.fetchAllUsers()
			.then(
					function(d) {
						self.users = d;
						console.log("get all the users in friendcontroller.js....")
					},
					function(errResponse) {
						console
								.error('Error while fetching Friendcontroller.js');
					});
};
self.fetchAllUsers();
self.getMyFriends();

	

} ]);