'use strict';

angular.module('stepApp').controller('InstCategoryTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstCategoryTemp',
        function($scope, $stateParams, $modalInstance, entity, InstCategoryTemp) {

        $scope.instCategoryTemp = entity;
        $scope.load = function(id) {
            InstCategoryTemp.get({id : id}, function(result) {
                $scope.instCategoryTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instCategoryTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instCategoryTemp.id != null) {
                InstCategoryTemp.update($scope.instCategoryTemp, onSaveFinished);
            } else {
                InstCategoryTemp.save($scope.instCategoryTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
