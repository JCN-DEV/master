'use strict';

angular.module('stepApp').controller('ModuleDialogController',
    ['$scope','$stateParams','entity','Module,$state',

        function($scope, $stateParams, entity, Module,$state) {

        $scope.module = entity;
        $scope.load = function(id) {
            Module.get({id : id}, function(result) {
                $scope.module = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:moduleUpdate', result);
            $state.go('module');
           // $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.module.id != null) {
                Module.update($scope.module, onSaveFinished);
            } else {
                Module.save($scope.module, onSaveFinished);
            }
        };

        $scope.clear = function() {
           // $modalInstance.dismiss('cancel');
        };
}]);
