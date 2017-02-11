'use strict';

angular.module('stepApp')
    .controller('HrEducationInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'HrEducationInfo','HrEmployeeInfo', 'MiscTypeSetup',
    function ($scope, $rootScope, $stateParams, DataUtils, entity, HrEducationInfo, HrEmployeeInfo, MiscTypeSetup) {
        $scope.hrEducationInfo = entity;
        $scope.load = function (id) {
            HrEducationInfo.get({id: id}, function(result) {
                $scope.hrEducationInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEducationInfoUpdate', function(event, result) {
            $scope.hrEducationInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);
        $scope.byteSize = DataUtils.byteSize;
    }]);
