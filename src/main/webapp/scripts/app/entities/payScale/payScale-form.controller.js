'use strict';

angular.module('stepApp').controller('PayScaleFormController',
    ['$scope', '$state', '$stateParams', 'entity', 'PayScale', 'User','$rootScope', 'GazetteSetup',
        function($scope, $state, $stateParams, entity, PayScale, User,$rootScope, GazetteSetup) {

        $scope.payScale = entity;
        $scope.users = User.query();
        $scope.gazettesetup = GazetteSetup.query();
        $scope.load = function(id) {
            PayScale.get({id : id}, function(result) {
                $scope.payScale = result;
                console.log('>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'+$scope.payScale);
            });
        };

        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:payScaleUpdate', result);
            $scope.isSaving = false;
            $state.go('payScale');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            $rootScope.setErrorMessage('Your Input is not correct.');
            $state.go('payScale');
        };

        $scope.save = function () {
            console.log($scope.payScale);
            $scope.isSaving = true;
            if ($scope.payScale.id != null) {
                PayScale.update($scope.payScale, onSaveSuccess, onSaveError);
            } else {
                PayScale.save($scope.payScale, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {

        };
}]);
