'use strict';

angular.module('stepApp')
    .controller('IisCurriculumInfoTempDetailController', function ($scope, $rootScope, $stateParams, entity, IisCurriculumInfoTemp, CmsCurriculum, Institute, User) {
        $scope.iisCurriculumInfoTemp = entity;
        $scope.load = function (id) {
            IisCurriculumInfoTemp.get({id: id}, function(result) {
                $scope.iisCurriculumInfoTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:iisCurriculumInfoTempUpdate', function(event, result) {
            $scope.iisCurriculumInfoTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
