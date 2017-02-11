'use strict';

angular.module('stepApp').controller('DrugsDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Drugs',
        function($scope, $stateParams, $modalInstance, entity, Drugs) {

        $scope.drugs = entity;
        $scope.load = function(id) {
            Drugs.get({id : id}, function(result) {
                $scope.drugs = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:drugsUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.drugs.id != null) {
                Drugs.update($scope.drugs, onSaveFinished);
            } else {
                Drugs.save($scope.drugs, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
