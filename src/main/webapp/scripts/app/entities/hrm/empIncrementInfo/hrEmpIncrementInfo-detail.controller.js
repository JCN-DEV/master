'use strict';

angular.module('stepApp')
    .controller('HrEmpIncrementInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmpIncrementInfo', 'HrEmployeeInfo', 'MiscTypeSetup', 'HrPayScaleSetup',
    function ($scope, $rootScope, $stateParams, entity, HrEmpIncrementInfo, HrEmployeeInfo, MiscTypeSetup, HrPayScaleSetup) {
        $scope.hrEmpIncrementInfo = entity;
        $scope.load = function (id) {
            HrEmpIncrementInfo.get({id: id}, function(result) {
                $scope.hrEmpIncrementInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpIncrementInfoUpdate', function(event, result) {
            $scope.hrEmpIncrementInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
