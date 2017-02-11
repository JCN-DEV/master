'use strict';

angular.module('stepApp').controller('ReligionDialogController',
    ['$scope', '$rootScope','$state', '$stateParams', 'entity', 'Religion',
        function($scope, $rootScope, $state, $stateParams, entity, Religion) {

        $scope.religion = entity;
        $scope.load = function(id) {
            Religion.get({id : id}, function(result) {
                $scope.religion = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:religionUpdate', result);
            $state.go('religion', null, { reload: true });
        };

        $scope.save = function () {
            if ($scope.religion.id != null) {
                Religion.update($scope.religion, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.religion.updated');
            } else {
                Religion.save($scope.religion, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.religion.created');
            }
        };

        $scope.clear = function() {
           $state.go('religion', null, { reload: true });
        };
}]);
