'use strict';

angular.module('stepApp')
    .controller('HrEmploymentInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEmploymentInfo', 'HrEmployeeInfo', 'HrEmplTypeInfo', 'HrPayScaleSetup',
    function ($scope, $rootScope, $stateParams, entity, HrEmploymentInfo, HrEmployeeInfo, HrEmplTypeInfo, HrPayScaleSetup) {
        $scope.hrEmploymentInfo = entity;
        $scope.load = function (id) {
            HrEmploymentInfo.get({id: id}, function(result) {
                $scope.hrEmploymentInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmploymentInfoUpdate', function(event, result) {
            $scope.hrEmploymentInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
