'use strict';

angular.module('stepApp')
    .controller('HrEmpAuditObjectionInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmpAuditObjectionInfo', 'HrEmployeeInfo', 'MiscTypeSetup',
    function ($scope, $rootScope, $stateParams, entity, HrEmpAuditObjectionInfo, HrEmployeeInfo, MiscTypeSetup) {
        $scope.hrEmpAuditObjectionInfo = entity;
        $scope.load = function (id) {
            HrEmpAuditObjectionInfo.get({id: id}, function(result) {
                $scope.hrEmpAuditObjectionInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpAuditObjectionInfoUpdate', function(event, result) {
            $scope.hrEmpAuditObjectionInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
