'use strict';

angular.module('stepApp')
    .controller('InstituteLabInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstituteLabInfo', 'InstituteInfraInfo',
    function ($scope, $rootScope, $stateParams, entity, InstituteLabInfo, InstituteInfraInfo) {
        $scope.instituteLabInfo = entity;
        $scope.load = function (id) {
            InstituteLabInfo.get({id: id}, function(result) {
                $scope.instituteLabInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instituteLabInfoUpdate', function(event, result) {
            $scope.instituteLabInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
