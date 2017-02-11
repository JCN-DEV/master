'use strict';

angular.module('stepApp')
    .controller('JpExperienceCategoryDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'JpExperienceCategory',
    function ($scope, $rootScope, $stateParams, entity, JpExperienceCategory) {
        $scope.jpExperienceCategory = entity;
        $scope.load = function (id) {
            JpExperienceCategory.get({id: id}, function(result) {
                $scope.jpExperienceCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:jpExperienceCategoryUpdate', function(event, result) {
            $scope.jpExperienceCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
