'use strict';

angular.module('stepApp')
    .controller('InstEmplPayscaleHistDetailController',
    ['$scope','$rootScope','$stateParams','entity','InstEmplPayscaleHist','InstEmployee','PayScale',
     function ($scope, $rootScope, $stateParams, entity, InstEmplPayscaleHist, InstEmployee, PayScale) {
        $scope.instEmplPayscaleHist = entity;
        $scope.load = function (id) {
            InstEmplPayscaleHist.get({id: id}, function(result) {
                $scope.instEmplPayscaleHist = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmplPayscaleHistUpdate', function(event, result) {
            $scope.instEmplPayscaleHist = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
