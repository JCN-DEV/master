'use strict';

angular.module('stepApp')
    .controller('EmployeeDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'Employee', 'User', 'Institute', 'PayScale', 'ApplicantEducation', 'Training', 'Skill', 'Reference', 'Lang',
     function ($scope, $rootScope, $stateParams, entity, Employee, User, Institute, PayScale, ApplicantEducation, Training, Skill, Reference, Lang) {
        $scope.employee = entity;
        $scope.load = function (id) {
            Employee.get({id: id}, function(result) {
                $scope.employee = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:employeeUpdate', function(event, result) {
            $scope.employee = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
