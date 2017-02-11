'use strict';

angular.module('stepApp')
    .controller('InstituteEmpCountDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstituteEmpCount', 'Institute',
    function ($scope, $rootScope, $stateParams, entity, InstituteEmpCount, Institute) {
        $scope.instituteEmpCount = entity;
        $scope.load = function (id) {
            InstituteEmpCount.get({id: id}, function(result) {
                $scope.instituteEmpCount = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instituteEmpCountUpdate', function(event, result) {
            $scope.instituteEmpCount = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
