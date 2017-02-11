'use strict';

angular.module('stepApp').controller('CatFormController',
['$scope', '$rootScope', '$state', '$stateParams', 'entity', 'Cat', 'OrganizationCategory',
        function($scope, $rootScope, $state, $stateParams, entity, Cat, OrganizationCategory) {

        $scope.cat = {};
        $scope.cat.status = true;
        $scope.categorys = OrganizationCategory.query();

        Cat.get({id : $stateParams.id}, function(response){
            $scope.cat = response;
            console.log($scope.cat.status);
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:catUpdate', result);
            $scope.isSaving = false;
            $state.go('cat');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            $rootScope.setErrorMessage('stepApp.cat.error');
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if( typeof $scope.cat.status == 'undefined'){
                $scope.cat.status = true;
            }

            if ($scope.cat.id != null) {
                Cat.update($scope.cat, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.cat.updated');
            } else {
                Cat.save($scope.cat, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.cat.created');
            }
        };



}]);
