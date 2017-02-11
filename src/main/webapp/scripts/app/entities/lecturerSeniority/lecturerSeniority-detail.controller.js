'use strict';

angular.module('stepApp')
    .controller('LecturerSeniorityDetailController',
    ['$scope','$rootScope','$stateParams','entity','LecturerSeniority','Institute',
    function ($scope, $rootScope, $stateParams, entity, LecturerSeniority, Institute) {
        $scope.lecturerSeniority = entity;
        $scope.load = function (id) {
            LecturerSeniority.get({id: id}, function(result) {
                $scope.lecturerSeniority = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:lecturerSeniorityUpdate', function(event, result) {
            $scope.lecturerSeniority = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
