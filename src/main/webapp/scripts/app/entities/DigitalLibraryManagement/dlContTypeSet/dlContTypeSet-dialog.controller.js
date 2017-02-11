'use strict';

angular.module('stepApp').controller('DlContTypeSetDialogController',
    ['$scope','$rootScope','$state', '$stateParams', 'entity', 'DlContTypeSet','DlContTypeSetByName','DlContTypeSetByCode',
        function($scope, $rootScope, $state, $stateParams, entity, DlContTypeSet, DlContTypeSetByName, DlContTypeSetByCode) {

        $scope.dlContTypeSet = entity;
        $scope.dlContTypeSet.pStatus = true;
        $scope.load = function(id) {
            DlContTypeSet.get({id : id}, function(result) {
                $scope.dlContTypeSet = result;
            });
        };

        DlContTypeSetByName.get({name: $scope.dlContTypeSet.name}, function (dlContTypeSet) {

                              $scope.message = "The  File Type is already exist.";

                          });
        DlContTypeSetByCode.get({code: $scope.dlContTypeSet.code}, function (dlContTypeSet) {

                              $scope.message = "The  File Type is already exist.";

                          });

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlContTypeSetUpdate', result);
            $scope.isSaving = false;
            $state.go('libraryInfo.dlContTypeSet',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlContTypeSet.id != null) {
                DlContTypeSet.update($scope.dlContTypeSet, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.dlContTypeSet.updated');
            } else {
                DlContTypeSet.save($scope.dlContTypeSet, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.dlContTypeSet.created');
            }
        };
}]);
