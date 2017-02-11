'use strict';

angular.module('stepApp')
    .controller('HrEmpGovtDuesInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmpGovtDuesInfo', 'HrEmployeeInfo',
    function ($scope, $rootScope, $stateParams, entity, HrEmpGovtDuesInfo, HrEmployeeInfo) {
        $scope.hrEmpGovtDuesInfo = entity;
        $scope.load = function (id) {
            HrEmpGovtDuesInfo.get({id: id}, function(result) {
                $scope.hrEmpGovtDuesInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpGovtDuesInfoUpdate', function(event, result) {
            $scope.hrEmpGovtDuesInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
