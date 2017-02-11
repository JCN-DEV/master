'use strict';

angular.module('stepApp')
    .controller('InstitutePlayGroundInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstitutePlayGroundInfo', 'InstituteInfraInfo',
    function ($scope, $rootScope, $stateParams, entity, InstitutePlayGroundInfo, InstituteInfraInfo) {
        $scope.institutePlayGroundInfo = entity;
        $scope.load = function (id) {
            InstitutePlayGroundInfo.get({id: id}, function(result) {
                $scope.institutePlayGroundInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:institutePlayGroundInfoUpdate', function(event, result) {
            $scope.institutePlayGroundInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
