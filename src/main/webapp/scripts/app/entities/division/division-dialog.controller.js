'use strict';

angular.module('stepApp').controller('DivisionDialogController',
    ['$scope', '$rootScope','$stateParams', '$modalInstance', 'entity', 'Division', 'District', 'Country',
        function($scope, $rootScope, $stateParams, $modalInstance, entity, Division, District, Country) {

        $scope.division = entity;
        $scope.districts = District.query();
        $scope.countrys = Country.query();
        $scope.load = function(id) {
            Division.get({id : id}, function(result) {
                $scope.division = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:divisionUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.division.id != null) {
                Division.update($scope.division, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.division.updated');
            } else {
                Division.save($scope.division, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.division.created');
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
