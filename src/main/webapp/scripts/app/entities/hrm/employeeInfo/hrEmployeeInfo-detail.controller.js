'use strict';

angular.module('stepApp')
    .controller('HrEmployeeInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmployeeInfo', 'HrDepartmentSetup', 'HrDesignationSetup', 'User',
    function ($scope, $rootScope, $stateParams, entity, HrEmployeeInfo, HrDepartmentSetup, HrDesignationSetup, User) {
        $scope.hrEmployeeInfo = entity;
        $scope.load = function (id) {
            HrEmployeeInfo.get({id: id}, function(result) {
                $scope.hrEmployeeInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmployeeInfoUpdate', function(event, result) {
            $scope.hrEmployeeInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
