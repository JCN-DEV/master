'use strict';

angular.module('stepApp')
    .controller('InstEmployeeDetailController',
    ['$scope','$rootScope','$stateParams','DataUtils','entity','InstEmployee','Institute','Designation','Religion','Quota','CourseTech','GradeSetup','InstEmpAddress','InstEmpEduQuali','InstEmplHist',
    function ($scope, $rootScope, $stateParams, DataUtils, entity, InstEmployee, Institute, Designation, Religion, Quota, CourseTech, GradeSetup, InstEmpAddress, InstEmpEduQuali, InstEmplHist) {
        $scope.instEmployee = entity;
        $scope.load = function (id) {
            InstEmployee.get({id: id}, function(result) {
                $scope.instEmployee = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmployeeUpdate', function(event, result) {
            $scope.instEmployee = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
