
angular.module('myApp.controllers',[]);


angular.module('myApp.controllers')
    .controller('masterCtrl',
        ['$rootScope','$scope', '$http', '$timeout', '$interval', '$q',
            function ($rootScope, $scope, $http, $timeout, $interval, $q ) {


                $scope.M = {};
                $scope.logins=false;
                $scope.pass=false;
                $scope.M.tsp = [];
                $scope.enginestatus = {};
                const URL ="http://localhost:8445"


                //noinspection JSAnnotator,JSAnnotator
                $scope.xx = function(){
                    $http.get('http://localhost:8445/test')
                        .then(function(response) {
                            console.log('XXXXX' + response.resp);
                            $scope.myTest = response.data;
                        });




                };



                let callEngine = function (arg) {
                    $http({
                        url: URL +'/'+ arg,
                        method: 'GET',
                        params: {

                        }
                    }).success(function (dane) {
                        $scope.enginestatus = dane;
                    });
                };

                $scope.startEngine = function(ls) {
                    console.log("zapisuję logi: " + JSON.stringify(ls));
                    $http({
                        url: $rootScope.M.URL + '/slogs',
                        method: 'PUT',
                        headers: {'Content-Type': 'application/json'},
                        data: JSON.stringify(ls)
                    });
                    callEngine("start");
                };



                $scope.stopEngine = function(){
                    callEngine("stop");

                };


                $scope.loadEngineState =function () {
                    callEngine("status");
                };

                $scope.reverseEngine = function () {
                    callEngine("reverse");
                };

                $scope.loadEngineState();



                $scope.jd = function () {
                    $http.get($rootScope.M.URL + '/temp?id=1')
                        .then(function(response) {
                            $scope.myTest2 = response.data;
                        });
                };
                $scope.login = function () {
                    var username = $scope.username;
                    var password = $scope.password;

                    $http({
                        url: 'http://localhost:8445/biolodzy',
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        data: 'login='+username+'&password='+password
                    }).then(function(response) {
                        if(response.data.status == 'loggedin') {
                            user.userLoggedIn();
                            user.setName(response.data.user);
                            $location.path('/dashboard');
                        } else {
                            alert('invalid login');
                        }
                    })
                }







            }
        ]
    )

.controller('loginCtrl'[ function($scope, $http, $location, user) {

}]);
