'user strict'; /* strictly follows the case i.e--case sensitive */

app.service('UserService', [
		'$http',
		'$q',
		'$rootScope',
		function($http, $q, $rootScope) {
			console.log("User SERvices.... in user service//")
			var BASE_URL = 'http://localhost:8088/cobackend'
			return {
				/*getAllUsers : function() {
					console.log(" calling fetch all Users.. in user service.js//")
					return $http.get(BASE_URL + '/usreL')
					.then(
							function(response) {
								return response.data;
							}, null
							
							function(response){
								console.log('Calling all the users in user service.js ')
								return response.data;
							},
							function(errResponse){
								console.error('Error while fetching Users....userservice.js');
								return $q.reject(errResponse);
												} 

					);
				},*/
				
				fetchAllUsers:function(){
					return $http.get(BASE_URL+'/users')
					.then(
							function(response){
								console.log('Calling user in user service ')
								return response.data;
							},
							function(errResponse){
								console.error('Error while fetching UserDetails');
								return $q.reject(errResponse);
							}				
			);
		},
		createUser:function(user){
			return $http.post(BASE_URL+'/user/',user)
			.then(
					function(response){
						console.log('calling createUser in UserService');
						return response.data;
					},
					function(errResponse){
						console.error('Error while creating User');
						return $q.reject(errResponse);
					}				
		);
		},
				/*myProfile : function() {
					console.log("  Users.. profile..in user service//")
					return $http.get(BASE_URL + '/myProfile').then(
							function(response) {
								return response.data;
							}, null

					);
				},*/

				accept : function(id) {
					console.log("   calling approved.... in user service//")
					return $http.get(BASE_URL + '/accept/' + id).then(
							function(response) {
								return response.data;
							}, null

					);
				},

				reject : function(id, reasonn) {
					console.log("   calling reject....in user service//")
					return $http
							.get(BASE_URL + '/reject/' + id + '/' + reasonn)
							.then(function(response) {
								return response.data;
							}, null

							);
				},

				/*register : function(user) {
					console.log("   calling register....in user servive//")
					return $http.post(BASE_URL + '/register/', user).then(
							function(response) {
								console.log('calling register User in UserService');
								return response.data;
							}, null
							function(errResponse){
								console.error('Error while creating User');
								return $q.reject(errResponse);
							}	

					);*/
				/*},*/

				updateUser : function(user) {
					console.log("   updating userr....in user service//")
					return $http.put(BASE_URL + '/update/', user).then(
							function(response) {
								return response.data;
							}, null

					);
				},

				logout : function(user) {
					console.log("   loggout userr....in user service//")
					return $http.put(BASE_URL + '/logout/', user).then(
							function(response) {
								return response.data;
							}, null

					);
				},

				authenticate : function(user) {
					console.log("   calling the authenticate userr....in user service//")
					return $http.post(BASE_URL + '/authenticate/', user).then(
							function(response) {
								return response.data;
							}, function(errResponse) {
								console.error('Error while logging out ..in user service');
								return $q.reject(errResponse);
							}

					);
				},

			}
		}
		
		]

);
