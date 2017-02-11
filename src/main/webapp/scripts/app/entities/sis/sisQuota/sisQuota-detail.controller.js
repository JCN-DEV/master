'use strict';

angular.module('stepApp')
    .controller('SisQuotaDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'SisQuota',

    function ($scope, $rootScope, $stateParams, entity, SisQuota) {
        $scope.sisQuota = entity;
        $scope.load = function (id) {
            SisQuota.get({id: id}, function(result) {
                $scope.sisQuota = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:sisQuotaUpdate', function(event, result) {
            $scope.sisQuota = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
