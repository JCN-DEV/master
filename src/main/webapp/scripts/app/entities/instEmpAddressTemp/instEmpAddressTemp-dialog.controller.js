'use strict';

angular.module('stepApp').controller('InstEmpAddressTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstEmpAddressTemp', 'InstEmployee', 'Upazila',
        function($scope, $stateParams, $modalInstance, entity, InstEmpAddressTemp, InstEmployee, Upazila) {

        $scope.instEmpAddressTemp = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.upazilas = Upazila.query();
        $scope.load = function(id) {
            InstEmpAddressTemp.get({id : id}, function(result) {
                $scope.instEmpAddressTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instEmpAddressTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instEmpAddressTemp.id != null) {
                InstEmpAddressTemp.update($scope.instEmpAddressTemp, onSaveFinished);
            } else {
                InstEmpAddressTemp.save($scope.instEmpAddressTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
