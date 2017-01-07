var app = angular.module('myApp', [ 'ngRoute' ]);

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
		controller : 'UserController'

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

	/* ....login...... */

	.when('/login', {
		templateUrl : 'c_user/login.html',
		controller : 'LoginController'

	})

	/* ....logout...... */

	.when('/logout', {
		templateUrl : 'c_user/logout.html',
		controller : 'LogoutController'

	})

	.otherwise({
		redirectTo : '/'
	});
});
