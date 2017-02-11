'use strict';

angular.module('stepApp')
    .controller('HrEmpAcrInfoDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmpAcrInfo', 'HrEmployeeInfo',
     function ($scope, $rootScope, $stateParams, entity, HrEmpAcrInfo, HrEmployeeInfo) {
        $scope.hrEmpAcrInfo = entity;
        $scope.load = function (id) {
            HrEmpAcrInfo.get({id: id}, function(result) {
                $scope.hrEmpAcrInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpAcrInfoUpdate', function(event, result) {
            $scope.hrEmpAcrInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
