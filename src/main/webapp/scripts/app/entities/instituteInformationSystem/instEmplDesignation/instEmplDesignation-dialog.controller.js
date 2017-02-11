'use strict';

angular.module('stepApp').controller('InstEmplDesignationDialogController',
    ['$scope','$state','$rootScope', '$stateParams',  'entity', 'InstEmplDesignation','InstCategory','InstLevel','InstDesignationByName','InstDesignationByCode',
        function($scope,$state,$rootScope, $stateParams, entity, InstEmplDesignation,InstCategory,InstLevel,InstDesignationByName,InstDesignationByCode) {

        $scope.instEmplDesignation = {};
        $scope.instEmplDesignation.status = true;
            console.log($scope.instEmplDesignation.status);

            InstEmplDesignation.get({id : $stateParams.id}, function(result) {
            $scope.instEmplDesignation = result;
        }, function(response) {
            if(response.status == 404){
                $scope.instEmplDesignation.status = true;

            }

        });
         $scope.instCategories = InstCategory.query();
        $scope.instLevels = InstLevel.query();
        $scope.load = function(id) {

        };

      InstDesignationByName.get({name: $scope.instEmplDesignation.name}, function (instEmplDesignation) {

             $scope.message = "The  Name is already existed.";
          });
      InstDesignationByCode.get({code: $scope.instEmplDesignation.code}, function (instEmplDesignation) {

              $scope.message = "The  Code is already existed.";
          });
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmplDesignationUpdate', result);
/*
            $modalInstance.close(result);
*/
            $state.go('setup.instEmplDesignation',{},{reload:true});
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmplDesignation.id != null) {
                InstEmplDesignation.update($scope.instEmplDesignation, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instEmplDesignation.updated');
            } else {
                InstEmplDesignation.save($scope.instEmplDesignation, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instEmplDesignation.created');
            }
        };

        $scope.clear = function() {
            $state.go('setup.instEmplDesignation', null, { reload: true });
        };
}]);
