'use strict';

angular.module('stepApp')
    .controller('InstEmplHistDetailController',
    ['$scope','$rootScope','$stateParams','DataUtils','entity','InstEmplHist','InstEmployee',
     function ($scope, $rootScope, $stateParams, DataUtils, entity, InstEmplHist, InstEmployee) {
        $scope.instEmplHist = entity;
        $scope.load = function (id) {
            InstEmplHist.get({id: id}, function(result) {
                $scope.instEmplHist = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmplHistUpdate', function(event, result) {
            $scope.instEmplHist = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
