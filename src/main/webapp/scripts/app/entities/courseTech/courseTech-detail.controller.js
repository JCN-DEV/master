'use strict';

angular.module('stepApp')
    .controller('CourseTechDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'CourseTech',
    function ($scope, $rootScope, $stateParams, entity, CourseTech) {
        $scope.courseTech = entity;
        $scope.load = function (id) {
            CourseTech.get({id: id}, function(result) {
                $scope.courseTech = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:courseTechUpdate', function(event, result) {
            $scope.courseTech = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
