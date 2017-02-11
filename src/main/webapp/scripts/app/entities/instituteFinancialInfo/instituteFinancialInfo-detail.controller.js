'use strict';

angular.module('stepApp')
    .controller('InstituteFinancialInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstituteFinancialInfo',
    function ($scope, $rootScope, $stateParams, entity, InstituteFinancialInfo) {
        $scope.instituteFinancialInfo = entity;
        $scope.load = function (id) {
            InstituteFinancialInfo.get({id: id}, function(result) {
                $scope.instituteFinancialInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instituteFinancialInfoUpdate', function(event, result) {
            $scope.instituteFinancialInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
