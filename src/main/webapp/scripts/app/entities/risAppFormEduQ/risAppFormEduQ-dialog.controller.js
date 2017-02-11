'use strict';

angular.module('stepApp').controller('RisAppFormEduQDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'RisAppFormEduQ', 'RisNewAppFormTwo',
        function($scope, $stateParams, $modalInstance, entity, RisAppFormEduQ, RisNewAppFormTwo) {

        $scope.risAppFormEduQ = entity;
        $scope.risnewappformtwos = RisNewAppFormTwo.query();
        $scope.load = function(id) {
            RisAppFormEduQ.get({id : id}, function(result) {
                $scope.risAppFormEduQ = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:risAppFormEduQUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.risAppFormEduQ.id != null) {
                RisAppFormEduQ.update($scope.risAppFormEduQ, onSaveFinished);
            } else {
                RisAppFormEduQ.save($scope.risAppFormEduQ, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
