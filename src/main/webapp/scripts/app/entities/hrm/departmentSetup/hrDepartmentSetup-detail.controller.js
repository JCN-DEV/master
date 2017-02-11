'use strict';

angular.module('stepApp')
    .controller('HrDepartmentSetupDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'HrDepartmentSetup',
     function ($scope, $rootScope, $stateParams, entity, HrDepartmentSetup) {
        $scope.hrDepartmentSetup = entity;
        $scope.load = function (id) {
            HrDepartmentSetup.get({id: id}, function(result) {
                $scope.hrDepartmentSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrDepartmentSetupUpdate', function(event, result) {
            $scope.hrDepartmentSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
