'use strict';

angular.module('stepApp')
    .controller('NameCancelStatusLogController',
    ['$scope', '$rootScope', '$state','NameCnclApplicationLogEmployeeCode', '$stateParams',
    function ($scope, $rootScope, $state,NameCnclApplicationLogEmployeeCode, $stateParams) {


        $scope.applicationlogs =[];

        $scope.showSearchForm = true;
        NameCnclApplicationLogEmployeeCode.get({'code':$stateParams.code}, function(result) {
            console.log(result);
            $scope.applicationlogs = result;

        });

    }]);
