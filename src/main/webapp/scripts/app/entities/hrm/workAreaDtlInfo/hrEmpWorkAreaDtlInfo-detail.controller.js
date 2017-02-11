'use strict';

angular.module('stepApp')
    .controller('HrEmpWorkAreaDtlInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmpWorkAreaDtlInfo', 'MiscTypeSetup', 'Division', 'District', 'Upazila',
    function ($scope, $rootScope, $stateParams, entity, HrEmpWorkAreaDtlInfo, MiscTypeSetup, Division, District, Upazila) {
        $scope.hrEmpWorkAreaDtlInfo = entity;
        $scope.load = function (id) {
            HrEmpWorkAreaDtlInfo.get({id: id}, function(result) {
                $scope.hrEmpWorkAreaDtlInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpWorkAreaDtlInfoUpdate', function(event, result) {
            $scope.hrEmpWorkAreaDtlInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
