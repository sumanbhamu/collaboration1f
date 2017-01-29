
'use strict';

app.service('EventService',['$http','$q','$rootScope',function($http,$q,$rootScope){
	
	console.log("EventService..")
	
	var BASE_URL='http://localhost:7188/cobackend/'
		return{
		
		/* GET LIST OF ALL EVENTS ...........................................*/
		
fetchAllEvents:function(){
	        console.log("calling fetchAllEvents")
			return $http.get(BASE_URL+'/events')
			.then(
					function(response){
						return response.data;
					},
					function(errResponse){
						console.error('Error while fetching EventDetails');
						return $q.reject(errResponse);
					}				
	);
},


/* CREATE A EVENT ..................................*/

createEvent:function(event){
	console.log('caling create event in eventService')
	return $http.post(BASE_URL+'/event/',event)
	.then(
			function(response){
				return response.data;
			},
			function(errResponse){
				console.error('Error while creating Event in eventService.js');
				return $q.reject(errResponse);
			}				
);
},

/* UPDATE A EVENT................................................ */

updateEvent:function(event,id){
	return $http.put(BASE_URL+'/event/'+id,event)
	.then(
			function(response){
				return response.data;
			},
			function(errResponse){
				console.error('Error while updating Event in eventService.js');
				return $q.reject(errResponse);
			}				
);
},

/* DELETE A EVENT............................................... */

deleteEvent:function(id){
	return $http.delete(BASE_URL+'/event/'+id)
	.then(
			function(response){
				return response.data;
			},
			function(errResponse){
				console.error('Error while deleting Event.... in eventService.js');
				return $q.reject(errResponse);
			}				
);
},

/* GET SELECTED EVENT DETAILS */

getEvent: function(id){
	return $http.get(BASE_URL+'/event/'+id)
	.then(                                        //success handler,failure handler
			function(response){
				$rootScope.selectedEvent=response.data
				return response.data;
			},
			function(errResponse){
				console.error('Error while getting Event');
				return $q.reject(errResponse);
			}				
);
},


};  //return
	
}
]
);