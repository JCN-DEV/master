'use strict';

angular.module('stepApp').controller('InstituteInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'InstituteInfo', 'User',
        function($scope, $stateParams, $modalInstance, $q, entity, InstituteInfo, User) {

        $scope.instituteInfo = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            InstituteInfo.get({id : id}, function(result) {
                $scope.instituteInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            //$scope.$emit('stepApp:instituteInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instituteInfo.id != null) {
                InstituteInfo.update($scope.instituteInfo, onSaveSuccess, onSaveError);
            } else {
                InstituteInfo.save($scope.instituteInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
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
}]);
