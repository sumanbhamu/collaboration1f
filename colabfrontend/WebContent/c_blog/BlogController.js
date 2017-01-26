/*
app.controller('BlogController', function($scope) {
	
	console.log("This is from BlogController........");
	
	$scope.message = "Message from Blog Controller..."

} );
*/

	
'use strict';

app
		.controller(
				'BlogController',
				[
						'$scope',
						'BlogService',
						'$location',
						'$rootScope', 
						
						function($scope, BlogService, $location, $rootScope) {
							

							console.log('In blogController.....blogcontroler.js....... ')
																
							console.log("blogController.....js")
							var self = this;
							self.blog = {
									blog_id : '',
									title : '',
									user_id : '',
									created_date : '',
									blog_data : '',
									status : '',
									description : '',
									likes : '',
								
									errorCode : '',
									errorMessage : '',
							};
							self.blogs = [];

							
							self.blogcomment={
									id:'',
									comments:'',
									bcomment_date:'',
									user_id:'',
									blog_comment_data:'',
									blog_id:'',
									errorCode:'',
									errorMessage:''
							};
							self.blogcomments=[];
							
							self.getSelectedBlog = getBlog


							/* get ALL blogS LIST........ .................................*/
							function getBlog(blog_id) {
								console.log("blog id ---"+blog_id)
								BlogService
										.getBlog(blog_id)
										.then(
												function(d) {
													$location.path('/view_blog');
													console.log("get all the blogs in blogcontroller.js....")
												},
												function(errResponse) {
													console
															.error('Error while fetching blogs');
												});
							};
							

							/* FETCH blog method defination....... ................................................*/
							self.fetchAllBlog = function() {

								BlogService
										.fetchAllBlog()
										.then(
												function(d)
												{
													self.blogs=d;
												},
																																		
												function(errResponse) {
													console
															.error('Error while fetchingg blog...');
												});
								
							};

							self.createBlog = function(blog) {
								console.log("createblog in controller.js...")
								BlogService.createBlog(blog)
										.then(
												self.fetchAllBlog,
												
												function(errResponse) {
													console
															.error('Error while create blog.');
												});
							};

							

							self.reject = function(id) {
								console.log("reject...")
								var reason = prompt("Please enter the reason");
								blogService.reject(id, reason).then(
										function(d) {
											self.blog = d;
											self.fetchAllBlog
											$location.path("/manage_blogs")
											alert(self.blog.errorMessage)

										}, null);
							};

							//calling mthd whn it will execute for the 1st time
							self.fetchAllBlog();
							
							
							self.submit = function() {
								{
									console.log('Saving New blog', self.blog);
									self.createBlog(self.blog);
								}
								self.reset();
							};

							self.reset = function() {
								console.log('reset blog....',self.blog);
								self.blog = {
										blog_id : '',
										title : '',
										user_id : '',
										created_date : '',
										blog_data : '',
										status : '',
										description : '',
										likes : '',
									
										errorCode : '',
										errorMessage : '',
								};
								//$scope.myForm.$setPristine(); // reset Form
							};

							/* save/add  comments......... */
							
							self.addComment=function(id,blogcomment){
								BlogService.addComment(id,blogcomment).then(self.fetchAllBlogComment(),
										function(errResponse) {
											console.error('Error while adding a Blog Comment');
										});
							};
							
							self.doComment=function(id){
								self.addComment(id,self.blogcomment);
								self.resetComment();
							}


							self.resetComment=function(){
								console.log('reset the blog comments',self.blogcomment);
								self.blogcomment={
										id:'',
										comments:'',
										bcomment_date:'',
										user_id:'',
										blog_comment_data:'',
										blog_id:'',
										errorCode:'',
										errorMessage:''
								};
								}
							
							/* list of all blog comments....*/

							self.fetchAllBlogComment = function() {
								BlogService.fetchAllBlogComment().then(function(d) { 
									self.blogcomments = d;
								}, function(errResponse) {
									console.error('Error while fetching Blogs Comment in controller.js');
								});
							};
							self.fetchAllBlogComment();
							
							self.getSelectedBlogComment = getBlogComment

							function getBlogComment(id) {
								console.log("getting blogcomments in BlogController.... " + id)
								BlogService.fetchAllBlogComments(id).then(function(d) {
									self.blogcomments = d;
									$location.path('/view_blog_comment');
								}, function(errResponse) {
									console.error('Error while fetching blogs comments in controller.js....');
								});
							};
							

							
							
							
						} ]);

