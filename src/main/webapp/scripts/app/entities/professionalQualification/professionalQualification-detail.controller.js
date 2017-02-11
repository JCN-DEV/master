'use strict';

angular.module('stepApp')
    .controller('ProfessionalQualificationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'ProfessionalQualification', 'Employee', 'Institute',
    function ($scope, $rootScope, $stateParams, entity, ProfessionalQualification, Employee, Institute) {
        $scope.professionalQualification = entity;
        $scope.load = function (id) {
            ProfessionalQualification.get({id: id}, function(result) {
                $scope.professionalQualification = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:professionalQualificationUpdate', function(event, result) {
            $scope.professionalQualification = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
