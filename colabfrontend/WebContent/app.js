var app = angular.module('myApp', [ 'ngRoute','ngCookies']);

app.config(function($routeProvider) {
	$routeProvider

	/* ....home page...... */
	.when('/', {
		templateUrl : 'c_home/home.html',
		controller : 'HomeController'

	})
	/* ....about us...... */

	.when('/aboutUs', {
		templateUrl : 'c_about/aboutUs.html',

	})
		
	/* ....register...... */

	.when('/register', {
		templateUrl : 'c_user/register.html',
		controller : 'UserController',
			

	})
	
	/* ....blog...... */

	.when('/blog', {
		templateUrl : 'c_blog/blog.html',
		controller : 'BlogController'

	})

	/* ....friend...... */

	.when('/friend', {
		templateUrl : 'c_friend/friend.html',
		controller : 'FriendController'

	})

	/* ....job...... */

	.when('/job', {
		templateUrl : 'c_job/job.html',
		controller : 'JobController'

	})
	
	.when('/post_job',{
		templateUrl:'c_job/post_job.html',
		controller:'JobController'
		
	})
	
	.when('/search_job',{
		templateUrl:'c_job/search_job.html',
		controller:'JobController'
		
	})
	

	/* ....login...... */

	.when('/login', {
		templateUrl : 'c_user/login.html',
		controller : 'UserController'

	})

	/* ....logout...... */

	.when('/logout', {
		templateUrl : 'c_home/home.html',
		controller : 'HomeController'

	})

	.otherwise({
		redirectTo : '/'
	});
});
app.run( function ($rootScope, $location,$cookieStore, $http) {

	 $rootScope.$on('$locationChangeStart', function (event, next, current) {
		 console.log("$locationChangeStart")
		 //http://localhost:8080/Collaboration/addjob
	        // redirect to login page if not logged in and trying to access a restricted page
	        var restrictedPage = $.inArray($location.path(), ['','/','/search_job','/view_blog','/login', '/register','/list_blog']) === -1;
		 console.log("Navigating to page :" + $location.path())
	        console.log("restrictedPage:" +restrictedPage)
	        console.log("currentUser:" +$rootScope.currentUser)
	        var loggedIn = $rootScope.currentUser.userid;
	        
	        console.log("loggedIn:" +loggedIn)
	        
	        if(!loggedIn)
	        	{
	        	
	        	 if (restrictedPage) {
		        	  console.log("Navigating to login page:")
		        	

						            $location.path('/login');
		                }
	        	}
	        
			 else //logged in
	        	{
	        	
				 var role = $rootScope.currentUser.role;
				 var userRestrictedPage = $.inArray($location.path(), ["/post_job"]) == 0;
				 
				 if(userRestrictedPage && role!='admin' )
					 {
					 
					  alert("You can not do this operation as you are logged as : " + role )
					   $location.path('/login');
					 
					 }
				     
	        	
	        	}
	        
	 }
	       );
	 
	 
	 // keep user logged in after page refresh
    $rootScope.currentUser = $cookieStore.get('currentUser') || {};
    if ($rootScope.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.currentUser; 
    }

});
