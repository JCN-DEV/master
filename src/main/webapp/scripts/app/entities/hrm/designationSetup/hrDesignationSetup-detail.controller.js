'use strict';

angular.module('stepApp')
    .controller('HrDesignationSetupDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'HrDesignationSetup', 'HrClassInfo', 'HrGradeSetup',
     function ($scope, $rootScope, $stateParams, entity, HrDesignationSetup, HrClassInfo, HrGradeSetup) {
        $scope.hrDesignationSetup = entity;
        $scope.load = function (id) {
            HrDesignationSetup.get({id: id}, function(result) {
                $scope.hrDesignationSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrDesignationSetupUpdate', function(event, result) {
            $scope.hrDesignationSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
