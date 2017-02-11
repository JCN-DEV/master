'use strict';

angular.module('stepApp').controller('CityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'City', 'Country',
        function($scope, $stateParams, $modalInstance, entity, City, Country) {

        $scope.city = entity;
        $scope.countrys = Country.query();
        $scope.load = function(id) {
            City.get({id : id}, function(result) {
                $scope.city = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cityUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.city.id != null) {
                City.update($scope.city, onSaveSuccess, onSaveError);
            } else {
                City.save($scope.city, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
