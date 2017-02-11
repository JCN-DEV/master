'use strict';

angular.module('stepApp')
    .controller('HrProjectInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrProjectInfo', 'MiscTypeSetup',
    function ($scope, $rootScope, $stateParams, entity, HrProjectInfo, MiscTypeSetup) {
        $scope.hrProjectInfo = entity;
        $scope.load = function (id) {
            HrProjectInfo.get({id: id}, function(result) {
                $scope.hrProjectInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrProjectInfoUpdate', function(event, result) {
            $scope.hrProjectInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
