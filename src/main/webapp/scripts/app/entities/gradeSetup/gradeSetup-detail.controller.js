'use strict';

angular.module('stepApp')
    .controller('GradeSetupDetailController',
    ['$scope','$rootScope','$stateParams','entity','GradeSetup',
    function ($scope, $rootScope, $stateParams, entity, GradeSetup) {
        $scope.gradeSetup = entity;
        $scope.load = function (id) {
            GradeSetup.get({id: id}, function(result) {
                $scope.gradeSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:gradeSetupUpdate', function(event, result) {
            $scope.gradeSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
