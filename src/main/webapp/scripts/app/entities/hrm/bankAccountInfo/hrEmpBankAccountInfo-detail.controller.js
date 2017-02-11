'use strict';

angular.module('stepApp')
    .controller('HrEmpBankAccountInfoDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmpBankAccountInfo', 'HrEmployeeInfo', 'MiscTypeSetup',
     function ($scope, $rootScope, $stateParams, entity, HrEmpBankAccountInfo, HrEmployeeInfo, MiscTypeSetup) {
        $scope.hrEmpBankAccountInfo = entity;
        $scope.load = function (id) {
            HrEmpBankAccountInfo.get({id: id}, function(result) {
                $scope.hrEmpBankAccountInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpBankAccountInfoUpdate', function(event, result) {
            $scope.hrEmpBankAccountInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
