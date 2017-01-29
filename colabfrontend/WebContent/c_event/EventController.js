'use strict';

app.controller('EventController', [
		'$scope',
		'EventService',
		'$location',
		'$rootScope',
		function($scope, EventService, $location, $rootScope) {
			console.log("EventController...")
			var self = this; // self is alias name instead directly use "this-EventController.js"
			self.event = { // initialization
				event_id : '',
				event_name : '',
				held_date : '',
				venue : '',
				description : '',
				user_name : '',
				errorCode:'',
				errorMessage:''
			};
			self.events = [];
			
			/*GET SELECTED EVENT DETAILS...................................*/

			self.getSelectedEvent = getEvent

			function getEvent(id) {
				console.log("getting event id.... " + id)
				EventService.getEvent(id).then(function(d) {
					self.event = d;
					$location.path('/view_event');
				}, function(errResponse) {
					console.error('Error while fetching events in EventController.js..');
				});
			};

			/* GET LIST OF ALL EVENTS /.............................*/

			self.fetchAllEvents = function() {
				console.log("getting list of events")
				EventService.fetchAllEvents()
				.then(function(d) { // these methods are called call back methods
					self.events = d;
				}, function(errResponse) {
					console.error('Error while fetching Events  in EventController.js..');
				});
			};
			self.fetchAllEvents();

			/* CREATE A EVENT/....................................... */

			self.createEvent = function(event) {
				console.log('submit a new event..',self.event);
				EventService.createEvent(event)
				.then(function(d){
				   self.event=d;	
				},
						function(errResponse) {
							console.error('Error while creating Events in EventController.js..');
						});
			};

			/* UPDATE A EVENT............................................. */

			self.updateEvent = function(event) {
				EventService.updateEvent(event).then(self.fetchAllEvents,
						function(errResponse) {
							console.error('Error while updating Events in EventController.js..');
						});
			};

			/* DELETE A EVENT .......................................*/

			self.deleteEvent = function(id) {
				EventService.deleteEvent(id).then(self.fetchAllEvents,
						function(errResponse) {
							console.error('Error while deleting Events in EventController.js..');
						});
			};

			/* ON CLICKING SUBMIT BUTTON .............................*/

			self.submit = function() {
				
					console.log('Saving New Event...', self.event);
					self.event.user_name = $rootScope.currentUser.user_name
					self.createEvent(self.event);
				
				self.reset();
			};
			
			self.reset=function(){
				console.log('resetting the event',self.event);
				self.event={
						event_id : '',
						event_name : '',
						held_date : '',
						venue : '',
						description : '',
						user_name : '',
						errorCode:'',
						errorMessage:''
					};
				}


			
		} ]);