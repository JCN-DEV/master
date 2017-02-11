'use strict';

angular.module('stepApp')
    .controller('MpoCommitteeDescisionDetailController',
    ['$scope','$rootScope','$stateParams','entity','MpoCommitteeDescision','MpoCommitteePersonInfo','MpoApplication',
    function ($scope, $rootScope, $stateParams, entity, MpoCommitteeDescision, MpoCommitteePersonInfo, MpoApplication) {
        $scope.mpoCommitteeDescision = entity;
        $scope.load = function (id) {
            MpoCommitteeDescision.get({id: id}, function(result) {
                $scope.mpoCommitteeDescision = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:mpoCommitteeDescisionUpdate', function(event, result) {
            $scope.mpoCommitteeDescision = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
