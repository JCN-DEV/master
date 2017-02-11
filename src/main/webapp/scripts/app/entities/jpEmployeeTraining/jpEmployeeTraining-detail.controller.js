'use strict';

angular.module('stepApp')
    .controller('JpEmployeeTrainingDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'JpEmployeeTraining', 'JpEmployee', 'Country',
     function ($scope, $rootScope, $stateParams, entity, JpEmployeeTraining, JpEmployee, Country) {
        $scope.jpEmployeeTraining = entity;
        $scope.load = function (id) {
            JpEmployeeTraining.get({id: id}, function(result) {
                $scope.jpEmployeeTraining = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:jpEmployeeTrainingUpdate', function(event, result) {
            $scope.jpEmployeeTraining = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
