
//List modules which it uses
var app = angular.module('myApp', [
    'ngRoute',
    'myApp.controllers'
]);
app.config(['$routeProvider', function ($routeProvider) {
    const urlBase = 'partials/';

    $routeProvider.when('/', {
        templateUrl: urlBase + 'homeView.html',
        controller: 'masterCtrl'
        })
      .when('/view1', {
        templateUrl: urlBase + 'sterowanieView.html',
        controller: 'masterCtrl'

    }).when('/view2', {
        templateUrl: urlBase + 'monioringView.html',
        controller: 'masterCtrl'
    })
    .when('/view3', {
        templateUrl: urlBase + 'homeView.html',
        controller: 'masterCtrl'
    })
        .when('/view4',{
            templateUrl:urlBase + 'projectView.html',
            controller: 'masterCtrl'
        })

    ;
}]);

app.run(function ($rootScope) {
    console.log('Uruchamiam app.run');

    $rootScope.M = {};
    //Global properties
    $rootScope.M.URL = 'http://localhost:8445';
});



