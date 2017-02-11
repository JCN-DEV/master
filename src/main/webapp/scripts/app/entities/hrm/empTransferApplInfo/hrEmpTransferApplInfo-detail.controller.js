'use strict';

angular.module('stepApp')
    .controller('HrEmpTransferApplInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmpTransferApplInfo', 'HrEmployeeInfo', 'MiscTypeSetup', 'HrEmpWorkAreaDtlInfo', 'HrDesignationSetup',
    function ($scope, $rootScope, $stateParams, entity, HrEmpTransferApplInfo, HrEmployeeInfo, MiscTypeSetup, HrEmpWorkAreaDtlInfo, HrDesignationSetup) {
        $scope.hrEmpTransferApplInfo = entity;
        $scope.load = function (id) {
            HrEmpTransferApplInfo.get({id: id}, function(result) {
                $scope.hrEmpTransferApplInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpTransferApplInfoUpdate', function(event, result) {
            $scope.hrEmpTransferApplInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
