'use strict';

angular.module('stepApp')
    .controller('IisCourseInfoTempDetailController', function ($scope, $rootScope, $stateParams, entity, IisCourseInfoTemp, IisCurriculumInfo, CmsTrade, CmsSubject, Institute, User) {
        $scope.iisCourseInfoTemp = entity;
        $scope.load = function (id) {
            IisCourseInfoTemp.get({id: id}, function(result) {
                $scope.iisCourseInfoTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:iisCourseInfoTempUpdate', function(event, result) {
            $scope.iisCourseInfoTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
