'use strict';

app.controller(	'UserController', [	'$scope', 'UserService', '$location','$rootScope',/*'$cookieStore',*/
						'$http',
						function($scope, UserService, $location, $rootScope,
								/*$cookieStore,*/$http) {
							console.log("UserController.....js")
							var self = this;
							self.user = {userid : '',username : '', password : '', cpassword : '',	phno : '',status:'',
								 emailid : '',isOnline : '',	role : '',
								errorCode : '',	errorMessage : '',/* file : ''*/
							};
							self.users = [];
							
							 $scope.orderByMe = function(x) {
							        $scope.myOrderBy = x;
							    }
					
							/*self.getAllUsers = function() {
								console.log("fetchAllUsers..in controlller.js.")
								UserService
										.getAllUsers()
										.then(
												function(d) {
													self.userL = d;
												},
												function(errResponse) {
													console
															.error('Error while fetching Users ,,,,controller.js');
												});
							};
							
							//self.getAllUsers();

							self.register = function(user) {
								console.log("createUser... in user controller")
								UserService
										.register(user)
										.then(
												self.getAllUsers,
												function(errResponse) {
													console
															.error('Error while creating User.');
												});
							};
*/			
							 
							 /* FETCH ALL USERS LIST........ */
								self.fetchAllUsers = function() {
									UserService.fetchAllUsers().then(function(d) {
										self.users = d;
									}, function(errResponse) {
										console.error('Error while fetching Users');
									});
								};
								self.fetchAllUsers();

					/* CREATE USER .......*/
								self.createUser = function(user) {
									
									UserService.createUser(user)
									.then(self.fetchAllUsers,
											function(errResponse) {
												console.error('Error while creating User...');
											});
								};

							self.myProfile = function() {
								console.log("myProfile...")
								UserService
										.myProfile()
										.then(
												function(d) {
													self.user = d;
													$location.path("/myProfile")
												},
												function(errResponse) {
													console
															.error('Error while fetch profile.');
												});
							};
							
							self.accept = function(id) {
								console.log("accept...")
								UserService
										.accept(id)
										.then(
												function(d) {
													self.user = d;
													self.fetchAllUsers
													$location.path("/manage_users")
													alert(self.user.errorMessage)
													
												},
												
												function(errResponse) {
													console
															.error('Error while updating User.');
												});
							};
							
							self.reject = function( id) {
								console.log("reject...")
								var reason = prompt("Please enter the reason");
								UserService
										.reject(id,reason)
										.then(
												function(d) {
													self.user = d;
													self.fetchAllUsers
													$location.path("/manage_users")
													alert(self.user.errorMessage)
													
												},
												null );
							};

							self.updateUser = function(user, id) {
								console.log("updateUser...")
								UserService
										.updateUser(user, id)
										.then(
												self.fetchAllUsers,
												null);
							};

/*							self.authenticate = function(user) {
								console.log("authenticate...")
								UserService
										.authenticate(user)
										.then(

												function(d) {

													self.user = d;
													console
															.log("user.errorCode: "
																	+ self.user.errorCode)
													if (self.user.errorCode == "404")

													{
														alert(self.user.errorMessage)

														self.user.userid = "";
														self.user.password = "";

													} else { //valid credentials
														console
																.log("Valid credentials. Navigating to home page")
														$rootScope.currentUser = self.user
														$http.defaults.headers.common['Authorization'] = 'Basic '
																+ $rootScope.currentUser;
														$cookieStore
																.put(
																		'currentUser',
																		$rootScope.currentUser);
														$location.path('/');

													}

												},
												function(errResponse) {

													console
															.error('Error while authenticate Users');
												});
							};*/

							self.logout = function() {
								console.log("logout")
								$rootScope.currentUser = {};
								$cookieStore.remove('currentUser');
								UserService.logout()
								$location.path('/');

							}

						

							self.fetchAllUsers();

							self.login = function() {
								{
									console.log('login validation????????',
											self.user);
									self.authenticate(self.user);
								}

							};

							self.submit = function() {
								{
									console.log('Saving New User', self.user);
									self.createUser(self.user);
								}
								self.reset();
							};

							self.reset = function() {
								self.user = {
										userid : '',username : '', password : '', cpassword : '',	phno : '',status:'',
										 emailid : '',isOnline : '',	role : '',
										errorCode : '',	errorMessage : '',/* file : ''*/
								};
								$scope.myForm.$setPristine(); // reset Form
							};

						} ]);







































/*'use strict';

app.controller('UserController',   ['$scope','UserService','$location','$rootScope','$cookieStore','$http',
                                 function($scope,UserService,$location,$rootScope,$cookieStore,$http){
	
										console.log("User controller.....")
										
										// self--refers to UserController
										var self=this;
										self.user={
												userid:'',
												username:'',
												password:'',
												cpassword:'',
												emailid:'',
												phno:'',
												role:'',
												status:'',
												reason:'',
												isOnline:'',
												errorCode:'',
												errorMessage:'',
												file:''
													
										};
										self.users=[];
										
										for sorting
										$scope.orderByMe=function(x){
											$scope.myOrderBy=x;
										}
										
										
 to fetch all the users										
										self.getAllUsers=function(){
											console.log("fetching all user......")
											UserService
													.getAllUsers()
													.then(
															function(d)
						                    				{
																self.users=d;
															},
															function(errResponse)
															{
																console.error('Error while fetching Users..');
															}
														);
													
										};
						                
										
										
										
 to create an user---register											
										self.register = function(user) {
											console.log("createUser...")
											UserService
													.register(user)
													.then(
															self.getAllUsers,
															function(errResponse) {
																console
																		.error('Error while creating User....**');
															});
										};
									
										
										
										

										self.myProfile = function() {
											console.log("myProfile...")
											UserService
													.myProfile()
													.then(
															function(d) {
																self.user = d;
																$location.path("/myProfile")
															},
															function(errResponse) {
																console
																		.error('Error while fetch profile.*****');
															});
										};
										

										
										
										
										
										
										
}

]

);*/
