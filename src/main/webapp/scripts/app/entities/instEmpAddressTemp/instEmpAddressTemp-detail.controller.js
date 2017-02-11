'use strict';

angular.module('stepApp')
    .controller('InstEmpAddressTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstEmpAddressTemp, InstEmployee, Upazila) {
        $scope.instEmpAddressTemp = entity;
        $scope.load = function (id) {
            InstEmpAddressTemp.get({id: id}, function(result) {
                $scope.instEmpAddressTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmpAddressTempUpdate', function(event, result) {
            $scope.instEmpAddressTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
