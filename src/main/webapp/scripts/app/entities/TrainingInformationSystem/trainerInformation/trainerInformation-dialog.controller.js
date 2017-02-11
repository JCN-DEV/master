'use strict';

angular.module('stepApp').controller('TrainerInformationDialogController',
    ['$scope', '$stateParams',  'entity', 'TrainerInformation', 'TrainingInitializationInfo', 'HrEmployeeInfo',
        function($scope, $stateParams, entity, TrainerInformation, TrainingInitializationInfo, HrEmployeeInfo) {

        $scope.trainerInformation = entity;
        $scope.traininginitializationinfos = TrainingInitializationInfo.query();
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.load = function(id) {
            TrainerInformation.get({id : id}, function(result) {
                $scope.trainerInformation = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:trainerInformationUpdate', result);
        };

        $scope.save = function () {
            if ($scope.trainerInformation.id != null) {
                TrainerInformation.update($scope.trainerInformation, onSaveFinished);
            } else {
                TrainerInformation.save($scope.trainerInformation, onSaveFinished);
            }
        };

        $scope.clear = function() {

        };
}]);
