'use strict';

angular.module('stepApp')
    .controller('QuotaDetailController',
    ['$scope','$rootScope','$stateParams','DataUtils','entity','Quota',
    function ($scope, $rootScope, $stateParams, DataUtils, entity, Quota) {
        $scope.quota = entity;
        $scope.load = function (id) {
            Quota.get({id: id}, function(result) {
                $scope.quota = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:quotaUpdate', function(event, result) {
            $scope.quota = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
