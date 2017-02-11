'use strict';

angular.module('stepApp')
    .controller('HrEmpTrainingInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'HrEmpTrainingInfo', 'HrEmployeeInfo','MiscTypeSetup',
    function ($scope, $rootScope, $stateParams, DataUtils, entity, HrEmpTrainingInfo, HrEmployeeInfo, MiscTypeSetup) {
        $scope.hrEmpTrainingInfo = entity;
        $scope.load = function (id) {
            HrEmpTrainingInfo.get({id: id}, function(result) {
                $scope.hrEmpTrainingInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpTrainingInfoUpdate', function(event, result) {
            $scope.hrEmpTrainingInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
