'use strict';

angular.module('stepApp')
    .controller('ApplicantEducationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'ApplicantEducation', 'Employee', 'Institute', 'User',
    function ($scope, $rootScope, $stateParams, entity, ApplicantEducation, Employee, Institute, User) {
        $scope.applicantEducation = entity;
        $scope.load = function (id) {
            ApplicantEducation.get({id: id}, function(result) {
                $scope.applicantEducation = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:applicantEducationUpdate', function(event, result) {
            $scope.applicantEducation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
