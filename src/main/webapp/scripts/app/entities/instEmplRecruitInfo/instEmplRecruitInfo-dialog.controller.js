'use strict';

angular.module('stepApp').controller('InstEmplRecruitInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstEmplRecruitInfo', 'InstEmployee',
        function($scope, $stateParams, $modalInstance, entity, InstEmplRecruitInfo, InstEmployee) {

        $scope.instEmplRecruitInfo = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            InstEmplRecruitInfo.get({id : id}, function(result) {
                $scope.instEmplRecruitInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmplRecruitInfoUpdate', result);
            $scope.isSaving = false;

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmplRecruitInfo.id != null) {
                InstEmplRecruitInfo.update($scope.instEmplRecruitInfo, onSaveSuccess, onSaveError);
            } else {
                InstEmplRecruitInfo.save($scope.instEmplRecruitInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
