'user strict'; /* strictly follows the case i.e--case sensitive */

app.service('UserService', [
		'$http',
		'$q',
		'$rootScope',
		function($http, $q, $rootScope) {
			console.log("User SERvices.... in user service//")
			var BASE_URL = 'http://localhost:7188/cobackend/'
			return {
								
				fetchAllUsers:function(){
					return $http.get(BASE_URL+'/users')
					.then(
							function(response){
								console.log('Calling user in user service....... ')
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
						console.log('calling createUser in UserService......js');
						return response.data;
					},
					function(errResponse){
						console.error('Error while creating User');
						return $q.reject(errResponse);
					}				
		);
		},
				

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
								console.log("//loggin in user service .js....")
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
