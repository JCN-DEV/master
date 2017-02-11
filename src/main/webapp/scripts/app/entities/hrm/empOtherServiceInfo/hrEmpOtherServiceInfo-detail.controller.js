'use strict';

angular.module('stepApp')
    .controller('HrEmpOtherServiceInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmpOtherServiceInfo', 'HrEmployeeInfo',
    function ($scope, $rootScope, $stateParams, entity, HrEmpOtherServiceInfo, HrEmployeeInfo) {
        $scope.hrEmpOtherServiceInfo = entity;
        $scope.load = function (id) {
            HrEmpOtherServiceInfo.get({id: id}, function(result) {
                $scope.hrEmpOtherServiceInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpOtherServiceInfoUpdate', function(event, result) {
            $scope.hrEmpOtherServiceInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
