'use strict';

angular.module('stepApp').controller('InstLevelDialogController',
    ['$scope','$state','$rootScope', '$stateParams',  'entity', 'InstLevel','InstLevelByName','InstLevelByCode',
        function($scope,$state,$rootScope, $stateParams, entity, InstLevel,InstLevelByName,InstLevelByCode) {

        $scope.instLevel = entity;

            $scope.instLevel.pStatus = true;

            $scope.load = function(id) {
            InstLevel.get({id : id}, function(result) {
                $scope.instLevel = result;
            });
        };

  InstLevelByName.get({name: $scope.instLevel.name}, function (instLevel) {

             $scope.message = "The  Name is already existed.";
          });
           InstLevelByCode.get({code: $scope.instLevel.code}, function (instLevel) {

              $scope.message = "The  Code is already existed.";
          });
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instLevelUpdate', result);
/*
            $modalInstance.close(result);
*/
            $state.go('setup.instLevel',{},{reload:true});

            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instLevel.id != null) {
                InstLevel.update($scope.instLevel, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instLevel.updated');
            } else {

                InstLevel.save($scope.instLevel, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instLevel.created');
            }
        };

        $scope.clear = function() {
            $state.go('setup.instLevel', null, { reload: true });
        };
}]);
