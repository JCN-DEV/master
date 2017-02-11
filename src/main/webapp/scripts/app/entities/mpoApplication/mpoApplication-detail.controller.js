'use strict';

angular.module('stepApp')
    .controller('MpoApplicationDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'MpoApplication', 'Employee', 'Institute',
     function ($scope, $rootScope, $stateParams, entity, MpoApplication, Employee, Institute) {
        $scope.mpoApplication = entity;
        $scope.load = function (id) {
            MpoApplication.get({id: id}, function(result) {
                $scope.mpoApplication = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:mpoApplicationUpdate', function(event, result) {
            $scope.mpoApplication = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
