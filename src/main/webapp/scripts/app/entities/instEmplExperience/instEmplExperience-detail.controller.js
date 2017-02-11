'use strict';

angular.module('stepApp')
    .controller('InstEmplExperienceDetailController',
    ['$scope','$rootScope','$stateParams','DataUtils','entity','InstEmplExperience','InstEmployee',
     function ($scope, $rootScope, $stateParams, DataUtils, entity, InstEmplExperience, InstEmployee) {
        $scope.instEmplExperience = entity;
        $scope.load = function (id) {
            InstEmplExperience.get({id: id}, function(result) {
                $scope.instEmplExperience = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmplExperienceUpdate', function(event, result) {
            $scope.instEmplExperience = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
