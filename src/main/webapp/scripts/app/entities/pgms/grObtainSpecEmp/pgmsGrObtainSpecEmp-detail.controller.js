'use strict';

angular.module('stepApp')
    .controller('PgmsGrObtainSpecEmpDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'PgmsGrObtainSpecEmp', 'HrEmployeeInfo',
    function ($scope, $rootScope, $stateParams, entity, PgmsGrObtainSpecEmp, HrEmployeeInfo) {
        $scope.pgmsGrObtainSpecEmp = entity;
        $scope.load = function (id) {
            PgmsGrObtainSpecEmp.get({id: id}, function(result) {
                $scope.pgmsGrObtainSpecEmp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pgmsGrObtainSpecEmpUpdate', function(event, result) {
            $scope.pgmsGrObtainSpecEmp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
