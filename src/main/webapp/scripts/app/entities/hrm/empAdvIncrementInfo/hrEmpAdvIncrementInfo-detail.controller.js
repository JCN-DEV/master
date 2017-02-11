'use strict';

angular.module('stepApp')
    .controller('HrEmpAdvIncrementInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'HrEmpAdvIncrementInfo', 'HrEmployeeInfo', 'MiscTypeSetup',
    function ($scope, $rootScope, $stateParams, DataUtils, entity, HrEmpAdvIncrementInfo, HrEmployeeInfo, MiscTypeSetup) {
        $scope.hrEmpAdvIncrementInfo = entity;
        $scope.load = function (id) {
            HrEmpAdvIncrementInfo.get({id: id}, function(result) {
                $scope.hrEmpAdvIncrementInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpAdvIncrementInfoUpdate', function(event, result) {
            $scope.hrEmpAdvIncrementInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
