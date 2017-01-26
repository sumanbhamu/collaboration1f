'use strict'; /* strictly follows the case i.e--case sensitive */

app.service('BlogService', [
		'$http',
		'$q',
		'$rootScope',
		function($http, $q, $rootScope) {
			console.log("blog SERvices.... in blog service//")
			var BASE_URL = 'http://localhost:7188/cobackend'
			return {
								
				fetchAllBlog:function(){
					return $http.get(BASE_URL+'/blogs')
					.then(
							function(response){
								console.log('Calling blog in blog service....... ')
								return response.data;
							},
							function(errResponse){
								console.error('Error while fetching blogDetails');
								return $q.reject(errResponse);
							}				
			);
		},
		createBlog:function(blog){
			return $http.post(BASE_URL+'/blog/',blog)
			.then(
					function(response){
						console.log('calling createblog in blogService......js');
						return response.data;
					},
					function(errResponse){
						console.error('Error while creating blog');
						return $q.reject(errResponse);
					}				
		);
		},
		
		deleteBlog:function(id){
			return $http.delete(BASE_URL+'/blog/'+id)
			.then(
					function(response){
						console.log('calling deleteblog in blogService......js');
						return response.data;
					},
					function(errResponse){
						console.error('Error while delete blog');
						return $q.reject(errResponse);
					}				
		);
		},
		
		
		
		getBlog:function(id){
			return $http.get(BASE_URL+'/blog/'+id)
			.then(
					function(response){
						console.log('calling getblog in blogService......js');
						$rootScope.selectedBlog = response.data
						return response.data;
					},
					function(errResponse){
						console.error('Error while get blog');
						return $q.reject(errResponse);
					}				
		);
		},
				
		
		
		/* get list of blogs as per id.........*/

		fetchAllBlogComments:function(id){
			return $http.get(BASE_URL+'/blogscommentlistperblog/'+id)
			.then(
					function(response){
						return response.data;
					},
					function(errResponse){
						console.error('Error while fetching BlogComment List');
						return $q.reject(errResponse);
					}				
		);
		},

		/* list of blog comments.... */

		fetchAllBlogComment:function(){
			return $http.get(BASE_URL+'/blogscommentlist/')
			.then(
					function(response){
						return response.data;
					},
					function(errResponse){
						console.error('Error while fetching BlogComments List');
						return $q.reject(errResponse);
					}				
		);
		},

		
		/* save a comment......*/

		addComment:function(id,blogcomment){
			return $http.post(BASE_URL+'/blogcomment/'+id,blogcomment)
			.then(
					function(response){
						return response.data;
					},
					function(errResponse){
						console.error('Error while creating Blog Comment');
						return $q.reject(errResponse);
					}				
		);
		},



			}
		}
		
		]

);


