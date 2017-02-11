'use strict';

angular.module('stepApp')
    .controller('ConfigurationController',
     ['$scope', 'ConfigurationService',
     function ($scope, ConfigurationService) {
        ConfigurationService.get().then(function(configuration) {
            $scope.configuration = configuration;
        });
    }]);
