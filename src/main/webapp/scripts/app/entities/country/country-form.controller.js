'use strict';

angular.module('stepApp').controller('CountryFormController',
['$scope', '$rootScope', '$state', '$stateParams', 'entity', 'Country',
        function($scope, $rootScope, $state, $stateParams, entity, Country) {

        $scope.country = entity;
        $scope.load = function(id) {
            Country.get({id : id}, function(result) {
                $scope.country = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:countryUpdate', result);
            $scope.isSaving = false;
            $state.go('country');
        };

        $scope.save = function () {
            if ($scope.country.id != null) {
                Country.update($scope.country, onSaveSuccess);
                $rootScope.setWarningMessage('stepApp.country.updated');
            } else {
                Country.save($scope.country, onSaveSuccess);
                $rootScope.setSuccessMessage('stepApp.country.created');
            }
        };

}]);
