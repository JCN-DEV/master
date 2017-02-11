'use strict';

angular.module('stepApp').controller('InstGovBodyAccessTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'InstGovBodyAccessTemp', 'Institute', 'User',
        function($scope, $stateParams, $modalInstance, $q, entity, InstGovBodyAccessTemp, Institute, User) {

        $scope.instGovBodyAccessTemp = entity;
        $scope.institutes = Institute.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            InstGovBodyAccessTemp.get({id : id}, function(result) {
                $scope.instGovBodyAccessTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instGovBodyAccessTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instGovBodyAccessTemp.id != null) {
                InstGovBodyAccessTemp.update($scope.instGovBodyAccessTemp, onSaveFinished);
            } else {
                InstGovBodyAccessTemp.save($scope.instGovBodyAccessTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
