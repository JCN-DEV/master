'use strict';

angular.module('stepApp')
    .controller('InsAcademicInfoDetailController',
    ['$scope','$rootScope','$stateParams','entity','InsAcademicInfo','Institute',
     function ($scope, $rootScope, $stateParams, entity, InsAcademicInfo, Institute) {
        $scope.insAcademicInfo = entity;
        $scope.load = function (id) {
            InsAcademicInfo.get({id: id}, function(result) {
                $scope.insAcademicInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:insAcademicInfoUpdate', function(event, result) {
            $scope.insAcademicInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
