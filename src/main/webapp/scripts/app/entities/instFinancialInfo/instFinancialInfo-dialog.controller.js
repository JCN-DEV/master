'use strict';

angular.module('stepApp').controller('InstFinancialInfoDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'InstFinancialInfo',
        function($scope, $stateParams,$state, entity, InstFinancialInfo) {


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


        $scope.instFinancialInfo = entity;
        $scope.load = function(id) {
            InstFinancialInfo.get({id : id}, function(result) {
                $scope.instFinancialInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instFinancialInfoUpdate', result);
           // $modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('instGenInfo.instFinancialInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            $state.go('instGenInfo.instFinancialInfo');
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instFinancialInfo.id != null) {
                InstFinancialInfo.update($scope.instFinancialInfo, onSaveSuccess, onSaveError);
            } else {
                $scope.instFinancialInfo.status=0;
                InstFinancialInfo.save($scope.instFinancialInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
        $scope.instFinancialInfo = null;
        };
}]);
