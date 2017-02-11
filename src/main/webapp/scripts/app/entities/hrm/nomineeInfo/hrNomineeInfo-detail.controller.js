'use strict';

angular.module('stepApp')
    .controller('HrNomineeInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrNomineeInfo', 'HrEmployeeInfo',
    function ($scope, $rootScope, $stateParams, entity, HrNomineeInfo, HrEmployeeInfo) {
        $scope.hrNomineeInfo = entity;
        $scope.load = function (id) {
            HrNomineeInfo.get({id: id}, function(result) {
                $scope.hrNomineeInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrNomineeInfoUpdate', function(event, result) {
            $scope.hrNomineeInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
