'use strict';

angular.module('stepApp').controller('InstEmpSpouseInfoDialogController',
    ['$scope', '$stateParams', 'Principle', '$q', 'entity', 'InstEmpSpouseInfo', 'InstEmployee', 'InstEmpAddress',
        function($scope, $stateParams, Principle, $q, entity, InstEmpSpouseInfo, InstEmployee, InstEmpAddress) {

        $scope.instEmpSpouseInfo = entity;
        $scope.instemployees = InstEmployee.query({filter: 'instempspouseinfo-is-null'});
        $q.all([$scope.instEmpSpouseInfo.$promise, $scope.instemployees.$promise]).then(function() {
            if (!$scope.instEmpSpouseInfo.instEmployee.id) {
                return $q.reject();
            }
            return InstEmployee.get({id : $scope.instEmpSpouseInfo.instEmployee.id}).$promise;
        }).then(function(instEmployee) {
            $scope.instemployees.push(instEmployee);
        });
        $scope.instempaddresss = InstEmpAddress.query();
        $scope.load = function(id) {
            InstEmpSpouseInfo.get({id : id}, function(result) {
                $scope.instEmpSpouseInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmpSpouseInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmpSpouseInfo.id != null) {
                InstEmpSpouseInfo.update($scope.instEmpSpouseInfo, onSaveSuccess, onSaveError);
            } else {
                InstEmpSpouseInfo.save($scope.instEmpSpouseInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
