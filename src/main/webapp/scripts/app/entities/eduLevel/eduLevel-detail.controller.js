'use strict';

angular.module('stepApp')
    .controller('EduLevelDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'EduLevel',
    function ($scope, $rootScope, $stateParams, entity, EduLevel) {
        $scope.eduLevel = entity;
        $scope.load = function (id) {
            EduLevel.get({id: id}, function(result) {
                $scope.eduLevel = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:eduLevelUpdate', function(event, result) {
            $scope.eduLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
