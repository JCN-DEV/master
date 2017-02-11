'use strict';

angular.module('stepApp').controller('LecturerSeniorityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'LecturerSeniority', 'Institute',
        function($scope, $stateParams, $modalInstance, entity, LecturerSeniority, Institute) {

        $scope.lecturerSeniority = entity;
        $scope.institutes = Institute.query({size: 500});
        $scope.load = function(id) {
            LecturerSeniority.get({id : id}, function(result) {
                $scope.lecturerSeniority = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:lecturerSeniorityUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lecturerSeniority.id != null) {
                LecturerSeniority.update($scope.lecturerSeniority, onSaveSuccess, onSaveError);
            } else {
                LecturerSeniority.save($scope.lecturerSeniority, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
