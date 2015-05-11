'use strict';

var app = angular.module('devSearchApp', ['ngRoute', 'ngResource','ui.bootstrap']);
app.config(function($routeProvider, $resourceProvider) {
        $routeProvider
            .when('/profiles', {
                templateUrl: 'views/profileList.html',
                controller: 'ProfileListController'
            })
            .when('/fetch', {
                templateUrl: 'views/fetchProfile.html',
                controller: 'ProfileFetchController'
            })
            .otherwise({
                redirectTo:'/profiles'
            });
    });

var Profile = function(attrs){
    var self = {};
    var initAttrs = attrs || {};

    self['avgIosRating'] = 0;
    self['avgDroidRating'] = 0;
    for(var attr in initAttrs){
        if(initAttrs.hasOwnProperty(attr))
            self[attr] = initAttrs[attr];

        if('iosApps' == attr && undefined !== initAttrs[attr] && self[attr].length > 0){
            var iosAppsList = self[attr];
            var total = 0;
            var sum = 0;
            for(var i=0; i<iosAppsList.length; i++){
                var app = iosAppsList[i];
                sum += app['userRatingCount']*app['averageUserRating'];
                total += app['userRatingCount'];
            }
            self['avgIosRating'] = (total > 0 ? sum/total : 0);
        }

        if('androidApps' == attr && undefined !== initAttrs[attr] && self[attr].length > 0){
            var androidAppsList = self[attr];
            var total = 0;
            var sum = 0;
            for(var i=0; i<androidAppsList.length; i++){
                var app = androidAppsList[i];
                var avgUserRtg = parseFloat(app['rating']);
                sum += app['ratingsCount']*avgUserRtg;
                total += app['ratingsCount'];
            }
            self['avgDroidRating'] = (total > 0 ? sum/total : 0);
        }
    };

    return self;
};

app.controller('ProfileListController', ['$scope', '$http', function($scope, $http) {
    $scope.profiles = [];
    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.totalPages = 0;
    $scope.totalRecords = 0;
    $scope.rangeSize = 5;
    $scope.sort = 'default';

    $scope.fetchProfiles = function(){
        var params = {'page' : $scope.currentPage-1, 'sort': $scope.sort};
        $http.get('/profile', {params : params}).success(function(data){
            var profiles = data.content || [];
            $scope.totalPages = data.totalPages;
            $scope.totalRecords = data.totalElements;
            var profileObjs = [];
            for(var i = 0; i < profiles.length; i++){
                profileObjs.push(new Profile(profiles[i]));
            }
            $scope.profiles = profileObjs;
        });
    };
    $scope.pageChanged = function(){
        console.log('page changed ' + $scope.currentPage);
        $scope.fetchProfiles();
    };

    $scope.sortBy = function(value){
        console.log('sort changed ' + value);
        $scope.fetchProfiles();
    };

    $scope.fetchProfiles();

    $scope.selectProfile = function(profile){
        $scope.selectedProfile = profile;
    };

}]);

app.controller('ProfileDetailController', ['$scope', function($scope){
    $scope.p = $scope.parent.selectedProfile;
}]);

app.controller('ProfileFetchController', ['$scope', function(scope){

}]);