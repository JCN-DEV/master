'use strict';

angular.module('stepApp')
    .controller('JpAcademicQualificationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'JpAcademicQualification', 'JpEmployee',
    function ($scope, $rootScope, $stateParams, entity, JpAcademicQualification, JpEmployee) {
        $scope.jpAcademicQualification = entity;
        $scope.load = function (id) {
            JpAcademicQualification.get({id: id}, function(result) {
                $scope.jpAcademicQualification = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:jpAcademicQualificationUpdate', function(event, result) {
            $scope.jpAcademicQualification = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
