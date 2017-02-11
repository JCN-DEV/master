'use strict';

angular.module('stepApp')
    .controller('LangDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'Lang', 'Employee', 'User',
    function ($scope, $rootScope, $stateParams, entity, Lang, Employee, User) {
        $scope.lang = entity;
        $scope.load = function (id) {
            Lang.get({id: id}, function(result) {
                $scope.lang = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:langUpdate', function(event, result) {
            $scope.lang = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
