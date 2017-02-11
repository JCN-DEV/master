'use strict';

angular.module('stepApp').controller('JpExperienceCategoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'JpExperienceCategory',
        function($scope, $stateParams, $modalInstance, entity, JpExperienceCategory) {

        $scope.jpExperienceCategory = entity;
        $scope.load = function(id) {
            JpExperienceCategory.get({id : id}, function(result) {
                $scope.jpExperienceCategory = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:jpExperienceCategoryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.jpExperienceCategory.id != null) {
                JpExperienceCategory.update($scope.jpExperienceCategory, onSaveFinished);
            } else {
                JpExperienceCategory.save($scope.jpExperienceCategory, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
