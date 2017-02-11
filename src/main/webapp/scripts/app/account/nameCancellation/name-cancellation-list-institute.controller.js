'use strict';

angular.module('stepApp')
    .controller('NameCancelListForInstituteController',
    ['$scope','NameCnclApplicationListCurrInst',
        function ($scope,NameCnclApplicationListCurrInst) {

            $scope.nameCnclAppliedLists = [];
            NameCnclApplicationListCurrInst.query({page:0, size:100}, function(result){
                $scope.nameCnclAppliedLists = result;
            });
        }]);
