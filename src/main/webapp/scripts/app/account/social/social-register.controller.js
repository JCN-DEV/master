'use strict';

angular.module('stepApp')
    .controller('SocialRegisterController',
     ['$scope', '$filter', '$stateParams',
     function ($scope, $filter, $stateParams) {
        $scope.provider = $stateParams.provider;
        $scope.providerLabel = $filter('capitalize')($scope.provider);
        $scope.success = $stateParams.success;
        $scope.error = !$scope.success;
    }]);
