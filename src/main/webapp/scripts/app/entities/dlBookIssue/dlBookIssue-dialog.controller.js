/*
'use strict';

angular.module('stepApp').controller('DlBookIssueDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DlBookIssue', 'InstEmployee', 'DlContTypeSet', 'DlContCatSet', 'DlContSCatSet', 'DlBookReturn',
        function($scope, $stateParams, $modalInstance, entity, DlBookIssue, InstEmployee, DlContTypeSet, DlContCatSet, DlContSCatSet, DlBookReturn) {

        $scope.dlBookIssue = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.dlconttypesets = DlContTypeSet.query();
        $scope.dlcontcatsets = DlContCatSet.query();
        $scope.dlcontscatsets = DlContSCatSet.query();
        $scope.dlbookreturns = DlBookReturn.query();
        $scope.load = function(id) {
            DlBookIssue.get({id : id}, function(result) {
                $scope.dlBookIssue = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlBookIssueUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlBookIssue.id != null) {
                DlBookIssue.update($scope.dlBookIssue, onSaveSuccess, onSaveError);
            } else {
                DlBookIssue.save($scope.dlBookIssue, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
*/
