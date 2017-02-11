'use strict';

angular.module('stepApp')
    .controller('TempInstGenInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'TempInstGenInfo', 'InstituteInfo', 'Upazila',
    function ($scope, $rootScope, $stateParams, entity, TempInstGenInfo, InstituteInfo, Upazila) {
        $scope.tempInstGenInfo = entity;
        $scope.load = function (id) {
            TempInstGenInfo.get({id: id}, function(result) {
                $scope.tempInstGenInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:tempInstGenInfoUpdate', function(event, result) {
            $scope.tempInstGenInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
