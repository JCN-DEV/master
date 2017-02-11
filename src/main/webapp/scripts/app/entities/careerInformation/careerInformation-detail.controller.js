'use strict';

angular.module('stepApp')
    .controller('CareerInformationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'CareerInformation', 'Employee', 'User',
    function ($scope, $rootScope, $stateParams, entity, CareerInformation, Employee, User) {
        $scope.careerInformation = entity;
        $scope.load = function (id) {
            CareerInformation.get({id: id}, function(result) {
                $scope.careerInformation = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:careerInformationUpdate', function(event, result) {
            $scope.careerInformation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
