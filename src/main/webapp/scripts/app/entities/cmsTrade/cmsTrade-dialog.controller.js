'use strict';

angular.module('stepApp').controller('CmsTradeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CmsTrade', 'CmsCurriculum',
        function($scope, $stateParams, $modalInstance, entity, CmsTrade, CmsCurriculum) {

        $scope.cmsTrade = entity;
        $scope.cmscurriculums = CmsCurriculum.query();
        $scope.load = function(id) {
            CmsTrade.get({id : id}, function(result) {
                $scope.cmsTrade = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cmsTradeUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cmsTrade.id != null) {
                CmsTrade.update($scope.cmsTrade, onSaveSuccess, onSaveError);
            } else {
                CmsTrade.save($scope.cmsTrade, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
