 'use strict';

angular.module('stepApp')
    .controller('QrcodeGenLogDetailController',
    ['$scope','$rootScope','$stateParams','entity','QrcodeGenLog',
    function ($scope, $rootScope, $stateParams, entity, QrcodeGenLog) {
        $scope.qrcodeGenLog = entity;
        $scope.load = function (id) {
            QrcodeGenLog.get({id: id}, function(result) {
                $scope.qrcodeGenLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:qrcodeGenLogUpdate', function(event, result) {
            $scope.qrcodeGenLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
