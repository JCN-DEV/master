'use strict';

angular.module('stepApp').controller('InstEmpAddressDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstEmpAddress', 'Upazila', 'InstEmployee', 'InstEmpSpouseInfo',
        function($scope, $stateParams, $modalInstance, entity, InstEmpAddress, Upazila, InstEmployee, InstEmpSpouseInfo) {

        $scope.instEmpAddress = entity;
        $scope.upazilas = Upazila.query();
        $scope.instemployees = InstEmployee.query();
        $scope.instempspouseinfos = InstEmpSpouseInfo.query();
        $scope.load = function(id) {
            InstEmpAddress.get({id : id}, function(result) {
                $scope.instEmpAddress = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmpAddressUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmpAddress.id != null) {
                InstEmpAddress.update($scope.instEmpAddress, onSaveSuccess, onSaveError);
            } else {
                InstEmpAddress.save($scope.instEmpAddress, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
