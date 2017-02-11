'use strict';

angular.module('stepApp')
    .controller('JpEmployeeExperienceDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'JpEmployeeExperience', 'JpEmployee',
     function ($scope, $rootScope, $stateParams, entity, JpEmployeeExperience, JpEmployee) {
        $scope.jpEmployeeExperience = entity;
        $scope.load = function (id) {
            JpEmployeeExperience.get({id: id}, function(result) {
                $scope.jpEmployeeExperience = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:jpEmployeeExperienceUpdate', function(event, result) {
            $scope.jpEmployeeExperience = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
