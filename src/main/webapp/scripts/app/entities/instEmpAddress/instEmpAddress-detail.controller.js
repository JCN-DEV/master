'use strict';

angular.module('stepApp')
    .controller('InstEmpAddressDetailController',
    ['$scope','$rootScope','$stateParams','entity','InstEmpAddress','Upazila','InstEmployee','InstEmpSpouseInfo',
    function ($scope, $rootScope, $stateParams, entity, InstEmpAddress, Upazila, InstEmployee, InstEmpSpouseInfo) {
        $scope.instEmpAddress = entity;
        $scope.load = function (id) {
            InstEmpAddress.get({id: id}, function(result) {
                $scope.instEmpAddress = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmpAddressUpdate', function(event, result) {
            $scope.instEmpAddress = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
