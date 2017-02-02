	
'use strict';

app
		.controller(
				'UserController',
				[
						'$scope',
						'UserService',
						'$location',
						'$rootScope', '$cookieStore', 
						'$http',
						function($scope, UserService, $location, $rootScope,
						 $cookieStore, $http) {
							

							console.log('In LoginController.....usercontroler.js....... ')
										
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

							self.updateUser = function(user) {
								console.log("myProfile...")
								
								UserService.updateUser(user)
								
								
									.then(
												self.fetchAllUsers,
												function(d) {
													self.user = d;
													console.log("updateUser...in user controller.js")
													$location
															.path("/myProfile")
												},
												function(errResponse) {
													console
															.error('Error while update profile.');
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
															.path("/view_all_users")
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
											$location.path("/view_all_users")
											alert(self.user.errorMessage)

										}, null);
							};

							
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
												
												$rootScope.currentUser=self.user
												console.log('currentUser::****'+self.user)
												$rootScope.currentUser = self.user
														$http.defaults.headers.common['Authorization'] = 'Basic '
																+ $rootScope.currentUser;
														$cookieStore
																.put(
																		'currentUser',
																		$rootScope.currentUser);
														
														if(self.user.emailid=="ADMIN@gmail.com"&&self.user.password=="ADMIN"){
															$location.path('/adminHome')
															console.log("Valid credentials. Navigating to adminHome page")
															
														}
														else if ($rootScope.currentUser) {
															$location.path('/');
															console.log("Valid credentials. Navigating to home page")
														}
										
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
									errorMessage : '',
								};
								
							};

						} ]);

