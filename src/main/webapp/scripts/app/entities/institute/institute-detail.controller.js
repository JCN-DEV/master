'use strict';

angular.module('stepApp')
    .controller('InstituteDetailController',
    ['$scope','$rootScope','$stateParams','DataUtils','entity','Institute','User','Upazila','Course',
    function ($scope, $rootScope, $stateParams, DataUtils, entity, Institute, User, Upazila, Course) {
        $scope.institute = entity;
        $scope.load = function (id) {
            Institute.get({id: id}, function(result) {
                $scope.institute = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instituteUpdate', function(event, result) {
            $scope.institute = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
