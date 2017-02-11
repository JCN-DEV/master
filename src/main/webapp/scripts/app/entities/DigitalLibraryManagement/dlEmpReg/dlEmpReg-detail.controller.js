'use strict';

angular.module('stepApp')
    .controller('DlEmpRegDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'DlEmpReg', 'InstEmployee',
    function ($scope, $rootScope, $stateParams, entity, DlEmpReg, InstEmployee) {
        $scope.dlEmpReg = entity;
        $scope.load = function (id) {
            DlEmpReg.get({id: id}, function(result) {
                $scope.dlEmpReg = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dlEmpRegUpdate', function(event, result) {
            $scope.dlEmpReg = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
