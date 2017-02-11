'use strict';

angular.module('stepApp')
    .controller('NameCnclApplicationDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'NameCnclApplication', 'InstEmployee',
     function ($scope, $rootScope, $stateParams, entity, NameCnclApplication, InstEmployee) {
        $scope.nameCnclApplication = entity;
        $scope.load = function (id) {
            NameCnclApplication.get({id: id}, function(result) {
                $scope.nameCnclApplication = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:nameCnclApplicationUpdate', function(event, result) {
            $scope.nameCnclApplication = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
