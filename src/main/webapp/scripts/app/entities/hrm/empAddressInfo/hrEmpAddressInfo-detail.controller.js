'use strict';

angular.module('stepApp')
    .controller('HrEmpAddressInfoDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmpAddressInfo', 'HrEmployeeInfo',
     function ($scope, $rootScope, $stateParams, entity, HrEmpAddressInfo, HrEmployeeInfo) {
        $scope.hrEmpAddressInfo = entity;
        $scope.load = function (id) {
            HrEmpAddressInfo.get({id: id}, function(result) {
                $scope.hrEmpAddressInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpAddressInfoUpdate', function(event, result) {
            $scope.hrEmpAddressInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
