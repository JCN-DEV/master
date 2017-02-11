'use strict';

angular.module('stepApp')
    .controller('HrEmpProfMemberInfoDetailController',
    ['$scope', '$rootScope', '$stateParams','entity','HrEmpProfMemberInfo', 'HrEmployeeInfo',
    function ($scope, $rootScope, $stateParams, entity, HrEmpProfMemberInfo, HrEmployeeInfo) {
        $scope.hrEmpProfMemberInfo = entity;
        $scope.load = function (id) {
            HrEmpProfMemberInfo.get({id: id}, function(result) {
                $scope.hrEmpProfMemberInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpProfMemberInfoUpdate', function(event, result) {
            $scope.hrEmpProfMemberInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
