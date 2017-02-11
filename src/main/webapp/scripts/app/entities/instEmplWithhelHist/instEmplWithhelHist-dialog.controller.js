'use strict';

angular.module('stepApp').controller('InstEmplWithhelHistDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstEmplWithhelHist', 'InstEmployee',
        function($scope, $stateParams, $modalInstance, entity, InstEmplWithhelHist, InstEmployee) {

        $scope.instEmplWithhelHist = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            InstEmplWithhelHist.get({id : id}, function(result) {
                $scope.instEmplWithhelHist = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmplWithhelHistUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmplWithhelHist.id != null) {
                InstEmplWithhelHist.update($scope.instEmplWithhelHist, onSaveSuccess, onSaveError);
            } else {
                InstEmplWithhelHist.save($scope.instEmplWithhelHist, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
