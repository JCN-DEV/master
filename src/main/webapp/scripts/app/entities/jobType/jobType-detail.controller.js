'use strict';

angular.module('stepApp')
    .controller('JobTypeDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'JobType',
     function ($scope, $rootScope, $stateParams, entity, JobType) {
        $scope.jobType = entity;
        $scope.load = function (id) {
            JobType.get({id: id}, function(result) {
                $scope.jobType = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:jobTypeUpdate', function(event, result) {
            $scope.jobType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
