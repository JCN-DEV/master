'use strict';

angular.module('stepApp')
    .controller('EducationalQualificationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'EducationalQualification', 'Employee', 'User',
    function ($scope, $rootScope, $stateParams, entity, EducationalQualification, Employee, User) {
        $scope.educationalQualification = entity;
        $scope.load = function (id) {
            EducationalQualification.get({id: id}, function(result) {
                $scope.educationalQualification = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:educationalQualificationUpdate', function(event, result) {
            $scope.educationalQualification = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
