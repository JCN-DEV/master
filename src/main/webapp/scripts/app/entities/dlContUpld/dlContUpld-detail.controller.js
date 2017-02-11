'use strict';

angular.module('stepApp')
    .controller('DlContUpldDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'DlContUpld', 'DlContTypeSet', 'DlContCatSet', 'InstEmployee', 'DlFileType',
     function ($scope, $rootScope, $stateParams, entity, DlContUpld, DlContTypeSet, DlContCatSet, InstEmployee, DlFileType) {
        $scope.dlContUpld = entity;
        $scope.load = function (id) {
            DlContUpld.get({id: id}, function(result) {
                $scope.dlContUpld = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dlContUpldUpdate', function(event, result) {
            $scope.dlContUpld = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
