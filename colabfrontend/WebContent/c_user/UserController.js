'use strict';

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
										
										/*for sorting*/
										$scope.orderByMe=function(x){
											$scope.myOrderBy=x;
										}
										
										
/* to fetch all the users*/										
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
						                
										
										
										
/* to create an user---register*/											
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

);