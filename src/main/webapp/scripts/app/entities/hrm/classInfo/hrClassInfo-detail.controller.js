'use strict';

angular.module('stepApp')
    .controller('HrClassInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrClassInfo',
    function ($scope, $rootScope, $stateParams, entity, HrClassInfo) {
        $scope.hrClassInfo = entity;
        $scope.load = function (id) {
            HrClassInfo.get({id: id}, function(result) {
                $scope.hrClassInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrClassInfoUpdate', function(event, result) {
            $scope.hrClassInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
