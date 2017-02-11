'use strict';

angular.module('stepApp')
    .controller('HrDesignationHeadSetupDetailController', function ($scope, $rootScope, $stateParams, entity, HrDesignationHeadSetup) {
        $scope.hrDesignationHeadSetup = entity;
        $scope.load = function (id) {
            HrDesignationHeadSetup.get({id: id}, function(result) {
                $scope.hrDesignationHeadSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrDesignationHeadSetupUpdate', function(event, result) {
            $scope.hrDesignationHeadSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
