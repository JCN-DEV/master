'use strict';

angular.module('stepApp')
    .controller('BEdApplicationDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'BEdApplication', 'InstEmployee',
     function ($scope, $rootScope, $stateParams, entity, BEdApplication, InstEmployee) {
        $scope.bEdApplication = entity;
        $scope.load = function (id) {
            BEdApplication.get({id: id}, function(result) {
                $scope.bEdApplication = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:bEdApplicationUpdate', function(event, result) {
            $scope.bEdApplication = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
