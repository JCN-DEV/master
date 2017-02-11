'use strict';

angular.module('stepApp')
    .controller('DearnessAssignDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'DearnessAssign', 'PayScale',
    function ($scope, $rootScope, $stateParams, entity, DearnessAssign, PayScale) {
        $scope.dearnessAssign = entity;
        $scope.load = function (id) {
            DearnessAssign.get({id: id}, function(result) {
                $scope.dearnessAssign = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dearnessAssignUpdate', function(event, result) {
            $scope.dearnessAssign = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
