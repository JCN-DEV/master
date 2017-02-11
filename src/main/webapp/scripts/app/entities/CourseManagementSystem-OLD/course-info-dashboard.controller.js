'use strict';

angular.module('stepApp')
    .controller('CourseDashboardController',
    ['$scope', '$state', 'DataUtils', 'ParseLinks',
    function ($scope, $state, DataUtils, ParseLinks) {

           $scope.abbreviate = DataUtils.abbreviate;
           $scope.byteSize = DataUtils.byteSize;
    }]);
