'use strict';

angular.module('stepApp')
    .controller('TrainingInformationSystemController',
    ['$scope', '$state', '$modal', 'DataUtils', 'ParseLinks',
     function ($scope, $state, $modal, DataUtils, ParseLinks) {

           $scope.abbreviate = DataUtils.abbreviate;
           $scope.byteSize = DataUtils.byteSize;

    }]);
