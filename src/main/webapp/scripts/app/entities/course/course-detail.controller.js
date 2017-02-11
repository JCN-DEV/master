'use strict';

angular.module('stepApp')
    .controller('CourseDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'Course', 'Institute',
    function ($scope, $rootScope, $stateParams, entity, Course, Institute) {
        $scope.course = entity;
        $scope.load = function (id) {
            Course.get({id: id}, function(result) {
                $scope.course = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:courseUpdate', function(event, result) {
            $scope.course = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
