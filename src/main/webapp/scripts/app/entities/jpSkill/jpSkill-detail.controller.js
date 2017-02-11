'use strict';

angular.module('stepApp')
    .controller('JpSkillDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'JpSkill',
     function ($scope, $rootScope, $stateParams, entity, JpSkill) {
        $scope.jpSkill = entity;
        $scope.load = function (id) {
            JpSkill.get({id: id}, function(result) {
                $scope.jpSkill = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:jpSkillUpdate', function(event, result) {
            $scope.jpSkill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
