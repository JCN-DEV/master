'use strict';

angular.module('stepApp')
    .controller('InstEmpSpouseInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstEmpSpouseInfo', 'InstEmployee', 'InstEmpAddress',
    function ($scope, $rootScope, $stateParams, entity, InstEmpSpouseInfo, InstEmployee, InstEmpAddress) {
        $scope.instEmpSpouseInfo = entity;
        $scope.load = function (id) {
            InstEmpSpouseInfo.get({id: id}, function(result) {
                $scope.instEmpSpouseInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmpSpouseInfoUpdate', function(event, result) {
            $scope.instEmpSpouseInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
