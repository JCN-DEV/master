'use strict';

angular.module('stepApp')
    .controller('IisCourseInfoDetaileeeeeeeeeeController',
    ['$scope','$rootScope','$stateParams','entity','IisCourseInfo','IisCurriculumInfo','CmsTrade','CmsSubject',
     function ($scope, $rootScope, $stateParams, entity, IisCourseInfo, IisCurriculumInfo, CmsTrade, CmsSubject) {
        $scope.iisCourseInfo = entity;
        $scope.load = function (id) {
            IisCourseInfo.get({id: id}, function(result) {
                $scope.iisCourseInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:iisCourseInfoUpdate', function(event, result) {
            $scope.iisCourseInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
