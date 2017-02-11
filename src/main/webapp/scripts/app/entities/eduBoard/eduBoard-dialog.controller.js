'use strict';

angular.module('stepApp').controller('EduBoardDialogController',
    ['$scope', '$rootScope', '$stateParams', 'entity', '$state', 'EduBoard',
        function($scope, $rootScope, $stateParams, entity,$state, EduBoard) {

        $scope.eduBoard = entity;
        $scope.load = function(id) {
            EduBoard.get({id : id}, function(result) {
                $scope.eduBoard = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:eduBoardUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('eduBoard', null, { reload: true });
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.eduBoard.id != null) {
                EduBoard.update($scope.eduBoard, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.eduBoard.updated');
            } else {
                EduBoard.save($scope.eduBoard, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.eduBoard.created');
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };
}]);
