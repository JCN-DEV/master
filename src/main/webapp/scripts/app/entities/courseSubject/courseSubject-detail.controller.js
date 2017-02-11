'use strict';

angular.module('stepApp')
    .controller('CourseSubjectDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'CourseSubject', 'CourseTech',
     function ($scope, $rootScope, $stateParams, entity, CourseSubject, CourseTech) {
        $scope.courseSubject = entity;
        $scope.load = function (id) {
            CourseSubject.get({id: id}, function(result) {
                $scope.courseSubject = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:courseSubjectUpdate', function(event, result) {
            $scope.courseSubject = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
