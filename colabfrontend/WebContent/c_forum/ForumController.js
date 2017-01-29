'use strict';

app.controller('ForumController', [
		'$scope',
		'ForumService',
		'$location',
		'$rootScope','$http',
		function($scope, ForumService, $location, $rootScope,$http) {
			console.log("ForumController...")
			var self = this; // self is alias name instead of directly using "this-ForumController"
			self.forum = { // initialization
				forum_id : '',
				user_id : '',
				topic : '',
				forum_data : '',
				created_date : '',		
				errorCode:'',
				errorMessage:''
			};
			self.forums = [];
			
			/*GET SELECTED FORUM DETAILS.........................*/

			self.getSelectedForum = getForum

			function getForum(id) {
				console.log("getting forum! " + id)
				ForumService.getForum(id).then(function(d) {
					self.forum = d;
					$location.path('/view_forum');
				}, function(errResponse) {
					console.error('Error while fetching forums in forum controller.js');
				});
			};

			/* GET LIST OF ALL FORUMS ...................................*/

			self.fetchAllForums = function() {
				console.log("getting list of forums")
				ForumService.fetchAllForums()
				.then(function(d) { // these methods are called call back methods
					self.forums = d;
				}, function(errResponse) {
					console.error('Error while fetching Forums in forum controller.js');
				});
			};
			self.fetchAllForums();

			/* CREATE A FORUM............................................ */

			self.createForum = function(forum) {
				ForumService.createForum(forum).then(self.fetchAllForums,
						function(errResponse) {
							console.error('Error while creating Forums in forum controller.js');
						});
			};

			/* UPDATE A FORUM .............................................*/

			self.updateForum = function(forum) {
				ForumService.updateForum(forum).then(self.fetchAllForums,
						function(errResponse) {
							console.error('Error while updating Forums in forum controller.js');
						});
			};

			/* DELETE A FORUM............................................. */

			self.deleteForum = function(id) {
				ForumService.deleteForum(id).then(self.fetchAllForums,
						function(errResponse) {
							console.error('Error while deleting Forums in forum controller.js');
						});
			};

			/* ON CLICKING SUBMIT BUTTON......................................... */

			self.submit = function() {
				
					console.log('Saving New Forum', self.forum);
					
					self.createForum(self.forum);
				
				self.reset();
			};
			
			self.reset=function(){
				console.log('resetting the forum details to null',self.forum);
				self.forum={
						forum_id : '',
						user_id : '',
						topic : '',
						forum_data : '',
						created_date : '',		
						errorCode:'',
						errorMessage:''
					};
				}


			

		} ]);