'use strict';

angular.module('stepApp')
    .controller('HrGradeSetupDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrGradeSetup', 'HrClassInfo',
    function ($scope, $rootScope, $stateParams, entity, HrGradeSetup, HrClassInfo) {
        $scope.hrGradeSetup = entity;
        $scope.load = function (id) {
            HrGradeSetup.get({id: id}, function(result) {
                $scope.hrGradeSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrGradeSetupUpdate', function(event, result) {
            $scope.hrGradeSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
