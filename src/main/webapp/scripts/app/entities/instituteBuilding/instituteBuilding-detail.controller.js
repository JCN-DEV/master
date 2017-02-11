'use strict';

angular.module('stepApp')
    .controller('InstituteBuildingDetailController',
    ['$scope','$rootScope', '$stateParams','entity','InstituteBuilding',
    function ($scope, $rootScope, $stateParams, entity, InstituteBuilding) {
        $scope.instituteBuilding = entity;
        $scope.load = function (id) {
            InstituteBuilding.get({id: id}, function(result) {
                $scope.instituteBuilding = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instituteBuildingUpdate', function(event, result) {
            $scope.instituteBuilding = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
