'use strict';

app.service('FriendServic',['$http','$q','$rootScope',
                             function($http,$q,$rootScope){
                      
console.log('FriendService...')

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
					console.error('Error while fetching UserDetails');
					return $q.reject(errResponse);
				}				
);
},
	
	/*GET MY FRIENDS LIST...............................*/
	getMyFriends:function(){
		return $http.get(BASE_URL+'/myFriends')
		.then(
				function(response){
					return response.data;
				},
				function(errResponse){
					console.error('Error while fetching My Friends');
					return $q.reject(errResponse);
				}				
);
},


/*UPDATE FRIEND REQUEST.............................*/
updateFriendRequest:function(friend, id){
	return $http.put(BASE_URL+'/friend/'+id, friend)
	.then(
			function(response){
				return response.data;
			},
			function(errResponse){
				console.error('Error while updating Friend request');
				return $q.reject(errResponse);
			}				
);
},

/*DELETE FRIEND REQUEST...................................*/
deleteFriend:function(id){
	return $http.delete(BASE_URL+'/friend/'+id)
	.then(
			function(response){
				return response.data;
			},
			function(errResponse){
				console.error('Error while deleting Friend');
				return $q.reject(errResponse);
			}				
);
},
};

}
]
);