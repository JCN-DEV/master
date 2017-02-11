'use strict';

angular.module('stepApp').controller('UpazilaDialogController',
    ['$scope', '$rootScope','$stateParams', '$modalInstance', 'entity', 'Upazila', 'District',
        function($scope, $rootScope, $stateParams, $modalInstance, entity, Upazila, District) {

        $scope.upazila = entity;
        $scope.districts = District.query({size: 500});
        $scope.load = function(id) {
            Upazila.get({id : id}, function(result) {
                $scope.upazila = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:upazilaUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.upazila.id != null) {
                Upazila.update($scope.upazila, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.upazila.updated');
            } else {
                Upazila.save($scope.upazila, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.upazila.created');
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
