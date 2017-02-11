'use strict';

angular.module('stepApp')
    .controller('InstituteInfraInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstituteInfraInfo', 'Institute', 'InstBuilding', 'InstLand',
    function ($scope, $rootScope, $stateParams, entity, InstituteInfraInfo, Institute, InstBuilding, InstLand) {
        $scope.instituteInfraInfo = entity;
        $scope.load = function (id) {
            InstituteInfraInfo.get({id: id}, function(result) {
                $scope.instituteInfraInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instituteInfraInfoUpdate', function(event, result) {
            $scope.instituteInfraInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
