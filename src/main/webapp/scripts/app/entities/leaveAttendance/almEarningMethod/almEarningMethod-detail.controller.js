'use strict';

angular.module('stepApp')
    .controller('AlmEarningMethodDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmEarningMethod',
    function ($scope, $rootScope, $stateParams, entity, AlmEarningMethod) {
        $scope.almEarningMethod = entity;
        $scope.load = function (id) {
            AlmEarningMethod.get({id: id}, function(result) {
                $scope.almEarningMethod = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almEarningMethodUpdate', function(event, result) {
            $scope.almEarningMethod = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
