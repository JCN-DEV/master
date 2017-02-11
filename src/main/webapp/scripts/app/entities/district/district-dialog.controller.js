'use strict';

angular.module('stepApp').controller('DistrictDialogController',
    ['$scope', '$rootScope','$stateParams', '$modalInstance', 'entity', 'District', 'Division', 'Upazila',
        function($scope, $rootScope, $stateParams, $modalInstance, entity, District, Division, Upazila) {

        $scope.district = entity;
        $scope.divisions = Division.query({size: 500});
        $scope.upazilas = Upazila.query({size: 500});
        $scope.load = function(id) {
            District.get({id : id}, function(result) {
                $scope.district = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:districtUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('division.district', null, { reload: true });
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.district.id != null) {
                District.update($scope.district, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.district.updated');
            } else {
                District.save($scope.district, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.district.created');
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
