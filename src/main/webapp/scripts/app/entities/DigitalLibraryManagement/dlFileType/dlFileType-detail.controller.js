'use strict';

angular.module('stepApp')
    .controller('DlFileTypeDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'DlFileType',
     function ($scope, $rootScope, $stateParams, entity, DlFileType) {
        $scope.dlFileType = entity;
        $scope.load = function (id) {
            DlFileType.get({id: id}, function(result) {
                $scope.dlFileType = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dlFileTypeUpdate', function(event, result) {
            $scope.dlFileType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
