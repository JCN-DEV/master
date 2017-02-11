'use strict';

angular.module('stepApp')
    .controller('InstEmpEduQualiDetailController',
    ['$scope','$rootScope','$stateParams','DataUtils','entity','InstEmpEduQuali','InstEmployee',
     function ($scope, $rootScope, $stateParams, DataUtils, entity, InstEmpEduQuali, InstEmployee) {
        $scope.instEmpEduQuali = entity;
        $scope.load = function (id) {
            InstEmpEduQuali.get({id: id}, function(result) {
                $scope.instEmpEduQuali = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmpEduQualiUpdate', function(event, result) {
            $scope.instEmpEduQuali = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
