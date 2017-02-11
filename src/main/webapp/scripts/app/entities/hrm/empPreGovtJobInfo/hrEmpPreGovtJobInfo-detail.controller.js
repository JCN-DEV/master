'use strict';

angular.module('stepApp')
    .controller('HrEmpPreGovtJobInfoDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmpPreGovtJobInfo', 'HrEmployeeInfo',
     function ($scope, $rootScope, $stateParams, entity, HrEmpPreGovtJobInfo, HrEmployeeInfo) {
        $scope.hrEmpPreGovtJobInfo = entity;
        $scope.load = function (id) {
            HrEmpPreGovtJobInfo.get({id: id}, function(result) {
                $scope.hrEmpPreGovtJobInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpPreGovtJobInfoUpdate', function(event, result) {
            $scope.hrEmpPreGovtJobInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
