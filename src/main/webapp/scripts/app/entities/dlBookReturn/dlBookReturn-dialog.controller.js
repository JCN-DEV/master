'use strict';

angular.module('stepApp').controller('DlBookReturnDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DlBookReturn', 'DlBookIssue', 'InstEmployee',
        function($scope, $stateParams, $modalInstance, entity, DlBookReturn, DlBookIssue, InstEmployee) {

        $scope.dlBookReturn = entity;
        $scope.dlbookissues = DlBookIssue.query();
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            DlBookReturn.get({id : id}, function(result) {
                $scope.dlBookReturn = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlBookReturnUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlBookReturn.id != null) {
                DlBookReturn.update($scope.dlBookReturn, onSaveSuccess, onSaveError);
            } else {
                DlBookReturn.save($scope.dlBookReturn, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
