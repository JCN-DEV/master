'use strict';

angular.module('stepApp')
    .controller('ExperienceDetailController',
    ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Experience', 'Employee', 'Institute', 'User',
    function ($scope, $rootScope, $stateParams, DataUtils, entity, Experience, Employee, Institute, User) {
        $scope.experience = entity;
        $scope.load = function (id) {
            Experience.get({id: id}, function(result) {
                $scope.experience = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:experienceUpdate', function(event, result) {
            $scope.experience = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
