'use strict';

angular.module('stepApp').controller('DlEmpRegDialogController',
    ['$scope','$state', '$stateParams',  'entity', 'DlEmpReg', 'InstEmployee',
        function($scope,$state, $stateParams, entity, DlEmpReg, InstEmployee) {

        $scope.dlEmpReg = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            DlEmpReg.get({id : id}, function(result) {
                $scope.dlEmpReg = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:dlEmpRegUpdate', result);
            $scope.isSaving = false;
            $state.go('libraryInfo.dlEmpReg',{},{reload:true});

        };

        $scope.save = function () {
            $scope.isSaving = true;

            if ($scope.dlEmpReg.id != null) {
                DlEmpReg.update($scope.dlEmpReg, onSaveFinished);
            } else {
                DlEmpReg.save($scope.dlEmpReg, onSaveFinished);
                //$state.go('dlEmpReg');

            }
        };

}]);
