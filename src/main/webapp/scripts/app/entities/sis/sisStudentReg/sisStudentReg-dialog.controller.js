'use strict';

angular.module('stepApp').controller('SisStudentRegDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'SisStudentReg',
        function($scope, $state, $stateParams, entity, SisStudentReg) {

        $scope.sisStudentReg = entity;
        $scope.load = function(id) {
            SisStudentReg.get({id : id}, function(result) {
                $scope.sisStudentReg = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:sisStudentRegUpdate', result);
           // $modalInstance.close(result);
            $state.go('sisStudentReg');
        };

        $scope.save = function () {
            if ($scope.sisStudentReg.id != null) {
                SisStudentReg.update($scope.sisStudentReg, onSaveFinished);
            } else {
                SisStudentReg.save($scope.sisStudentReg, onSaveFinished);
            }
        };

        $scope.clear = function() {
           // $modalInstance.dismiss('cancel');
            $state.go('sisStudentReg');
        };
}]);
