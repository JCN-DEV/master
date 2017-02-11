'use strict';

angular.module('stepApp')
    .controller('InstituteDashboardController',
    ['$scope','Principal','InstituteByLogin',
    function ($scope, Principal, InstituteByLogin) {

    InstituteByLogin.query({},function(result){
        $scope.logInInstitute = result;
        console.log("======================");
        console.log(result);
    });

    }]);
