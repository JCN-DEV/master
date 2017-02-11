'use strict';

angular.module('stepApp')
    .controller('PrlEmpGenSalDetailInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlEmpGenSalDetailInfo, PrlEmpGeneratedSalInfo) {
        $scope.prlEmpGenSalDetailInfo = entity;
        $scope.load = function (id) {
            PrlEmpGenSalDetailInfo.get({id: id}, function(result) {
                $scope.prlEmpGenSalDetailInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlEmpGenSalDetailInfoUpdate', function(event, result) {
            $scope.prlEmpGenSalDetailInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
