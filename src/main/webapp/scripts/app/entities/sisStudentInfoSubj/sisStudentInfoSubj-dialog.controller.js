'use strict';

angular.module('stepApp').controller('SisStudentInfoSubjDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'SisStudentInfoSubj', 'SisStudentInfo',
        function($scope, $stateParams, $modalInstance, entity, SisStudentInfoSubj, SisStudentInfo) {

        $scope.sisStudentInfoSubj = entity;
        $scope.sisstudentinfos = SisStudentInfo.query();
        $scope.load = function(id) {
            SisStudentInfoSubj.get({id : id}, function(result) {
                $scope.sisStudentInfoSubj = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:sisStudentInfoSubjUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.sisStudentInfoSubj.id != null) {
                SisStudentInfoSubj.update($scope.sisStudentInfoSubj, onSaveFinished);
            } else {
                SisStudentInfoSubj.save($scope.sisStudentInfoSubj, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
