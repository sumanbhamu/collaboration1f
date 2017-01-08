	
'use strict';

app
		.controller(
				'UserController',
				[
						'$scope',
						'UserService',
						'$location',
						'$rootScope',/* '$cookieStore', */
						'$http',
						function($scope, UserService, $location, $rootScope,
						/* $cookieStore, */$http) {
							

							console.log('In LoginController.....usercontroler.js....... ')
									//$scope.message="You successfully logged In"
										
									$rootScope.isLoggedIn="true"
							
							
							console.log("UserController.....js")
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
								errorMessage : '',/* file : '' */
							};
							self.users = [];

							$scope.orderByMe = function(x) {
								$scope.myOrderBy = x;
							}


							/* FETCH ALL USERS LIST........ .................................*/
							self.fetchAllUsers = function() {
								UserService
										.fetchAllUsers()
										.then(
												function(d) {
													self.users = d;
													console.log("get all the users in usercontroller.js....")
												},
												function(errResponse) {
													console
															.error('Error while fetching Users');
												});
							};
							self.fetchAllUsers();

							/* CREATE USER ....... ................................................*/
							self.createUser = function(user) {

								UserService
										.createUser(user)
										.then(
												
												self.fetchAllUsers,
												
												function(errResponse) {
													console
															.error('Error while creating User...');
												});
								
							};

							self.myProfile = function() {
								console.log("myProfile...")
								UserService
										.myProfile()
										.then(
												function(d) {
													self.user = d;
													$location
															.path("/myProfile")
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
													$location
															.path("/manage_users")
													alert(self.user.errorMessage)

												},

												function(errResponse) {
													console
															.error('Error while updating User.');
												});
							};

							self.reject = function(id) {
								console.log("reject...")
								var reason = prompt("Please enter the reason");
								UserService.reject(id, reason).then(
										function(d) {
											self.user = d;
											self.fetchAllUsers
											$location.path("/manage_users")
											alert(self.user.errorMessage)

										}, null);
							};

							self.updateUser = function(user, id) {
								console.log("updateUser...")
								UserService.updateUser(user, id).then(
										self.fetchAllUsers, null);
							};

							//self.fetchAllUsers();

							self.login = function() {
								{
									console.log('login validation????????',self.user);
									self.authenticate(self.user);
								}

							};
							
							
							self.authenticate = function(user) {
								UserService.authenticate(user)
								.then(function(d) {
									self.user = d;
								
									if (self.user.errorCode == "404")

									{
										alert(self.user.errorMessage)
										console.log("error in loggin in user controller.js..")

										self.user.userid = "";
										self.user.password = "";

									} else { // valid
										// credentials
										
											console.log("user login in user controller.js.....")
											$rootScope.isLoggedIn="true"
											$location.path('/');
									    	console.log("Valid credentials. Navigating to home page")
									
									}
								},
									function(errResponse) {
									console.error('Error while authenticating User...');
								});
							};
							
							
							
							
							self.logout = function() {
								console.log("logout")
								$rootScope.currentUser = {};
								$cookieStore.remove('currentUser');
								UserService.logout()
										
								$location.path('/');

							}


							self.submit = function() {
								{
									console.log('Saving New User', self.user);
									self.createUser(self.user);
								}
								self.reset();
							};

							self.reset = function() {
								console.log('reset user....',self.user);
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
									errorMessage : '',/* file : '' */
								};
								//$scope.myForm.$setPristine(); // reset Form
							};

						} ]);

/*
 * 'use strict';
 * 
 * app.controller('UserController',
 * ['$scope','UserService','$location','$rootScope','$cookieStore','$http',
 * function($scope,UserService,$location,$rootScope,$cookieStore,$http){
 * 
 * console.log("User controller.....")
 *  // self--refers to UserController var self=this; self.user={ userid:'',
 * username:'', password:'', cpassword:'', emailid:'', phno:'', role:'',
 * status:'', reason:'', isOnline:'', errorCode:'', errorMessage:'', file:''
 *  }; self.users=[];
 * 
 * for sorting $scope.orderByMe=function(x){ $scope.myOrderBy=x; }
 * 
 * 
 * to fetch all the users self.getAllUsers=function(){ console.log("fetching all
 * user......") UserService .getAllUsers() .then( function(d) { self.users=d; },
 * function(errResponse) { console.error('Error while fetching Users..'); } );
 *  };
 * 
 * 
 * 
 * 
 * to create an user---register self.register = function(user) {
 * console.log("createUser...") UserService .register(user) .then(
 * self.getAllUsers, function(errResponse) { console .error('Error while
 * creating User....**'); }); };
 * 
 * 
 * 
 * 
 * 
 * self.myProfile = function() { console.log("myProfile...") UserService
 * .myProfile() .then( function(d) { self.user = d; $location.path("/myProfile") },
 * function(errResponse) { console .error('Error while fetch profile.*****');
 * }); };
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  }
 *  ]
 *  );
 */
