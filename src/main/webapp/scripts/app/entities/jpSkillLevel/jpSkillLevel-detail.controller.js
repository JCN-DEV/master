'use strict';

angular.module('stepApp')
    .controller('JpSkillLevelDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'JpSkillLevel',
     function ($scope, $rootScope, $stateParams, entity, JpSkillLevel) {
        $scope.jpSkillLevel = entity;
        $scope.load = function (id) {
            JpSkillLevel.get({id: id}, function(result) {
                $scope.jpSkillLevel = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:jpSkillLevelUpdate', function(event, result) {
            $scope.jpSkillLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
