'use strict';

angular.module('stepApp')
    .controller('CourseDashboardController',
        ['$scope', '$state', 'DataUtils',
        function ($scope, $state, DataUtils) {

           $scope.abbreviate = DataUtils.abbreviate;
           $scope.byteSize = DataUtils.byteSize;
    }]);
