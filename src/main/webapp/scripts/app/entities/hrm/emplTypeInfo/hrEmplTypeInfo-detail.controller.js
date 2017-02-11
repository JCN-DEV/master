'use strict';

angular.module('stepApp')
    .controller('HrEmplTypeInfoDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmplTypeInfo',
     function ($scope, $rootScope, $stateParams, entity, HrEmplTypeInfo) {
        $scope.hrEmplTypeInfo = entity;
        $scope.load = function (id) {
            HrEmplTypeInfo.get({id: id}, function(result) {
                $scope.hrEmplTypeInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmplTypeInfoUpdate', function(event, result) {
            $scope.hrEmplTypeInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
