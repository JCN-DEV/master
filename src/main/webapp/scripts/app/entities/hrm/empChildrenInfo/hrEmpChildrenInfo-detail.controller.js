'use strict';

angular.module('stepApp')
    .controller('HrEmpChildrenInfoDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmpChildrenInfo', 'HrEmployeeInfo',
     function ($scope, $rootScope, $stateParams, entity, HrEmpChildrenInfo, HrEmployeeInfo) {
        $scope.hrEmpChildrenInfo = entity;
        $scope.load = function (id) {
            HrEmpChildrenInfo.get({id: id}, function(result) {
                $scope.hrEmpChildrenInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpChildrenInfoUpdate', function(event, result) {
            $scope.hrEmpChildrenInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
