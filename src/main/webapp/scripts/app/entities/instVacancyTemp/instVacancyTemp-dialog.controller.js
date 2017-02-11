'use strict';

angular.module('stepApp').controller('InstVacancyTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstVacancyTemp', 'Institute', 'CmsTrade', 'CmsSubject', 'InstEmplDesignation',
        function($scope, $stateParams, $modalInstance, entity, InstVacancyTemp, Institute, CmsTrade, CmsSubject, InstEmplDesignation) {

        $scope.instVacancyTemp = entity;
        $scope.institutes = Institute.query();
        $scope.cmstrades = CmsTrade.query();
        $scope.cmssubjects = CmsSubject.query();
        $scope.instempldesignations = InstEmplDesignation.query();
        $scope.load = function(id) {
            InstVacancyTemp.get({id : id}, function(result) {
                $scope.instVacancyTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instVacancyTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instVacancyTemp.id != null) {
                InstVacancyTemp.update($scope.instVacancyTemp, onSaveFinished);
            } else {
                InstVacancyTemp.save($scope.instVacancyTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
