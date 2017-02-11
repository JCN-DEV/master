'use strict';

angular.module('stepApp')
    .controller('CourseSubDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'CourseSub', 'CourseTech',
    function ($scope, $rootScope, $stateParams, entity, CourseSub, CourseTech) {
        $scope.courseSub = entity;
        $scope.load = function (id) {
            CourseSub.get({id: id}, function(result) {
                $scope.courseSub = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:courseSubUpdate', function(event, result) {
            $scope.courseSub = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
