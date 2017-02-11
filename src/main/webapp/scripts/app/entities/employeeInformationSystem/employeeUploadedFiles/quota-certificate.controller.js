angular.module('stepApp')
    .controller('EmployeeQuotaCertificateController',
    ['$scope', '$state', '$modal', 'DataUtils', 'InstEmployee', 'InstEmployeeSearch', 'ParseLinks',
     function ($scope, $state, $modal, DataUtils, InstEmployee, InstEmployeeSearch, ParseLinks) {

           $scope.abbreviate = DataUtils.abbreviate;
           $scope.byteSize = DataUtils.byteSize;
    }]);
