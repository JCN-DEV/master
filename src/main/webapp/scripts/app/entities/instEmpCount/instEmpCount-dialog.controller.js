'use strict';

angular.module('stepApp').controller('InstEmpCountDialogController',
    ['$scope', '$stateParams', '$q','$state' ,'entity', 'InstEmpCount', 'Institute',
        function($scope, $stateParams, $q,$state, entity, InstEmpCount, Institute) {

        $scope.instEmpCount = entity;
        $scope.institutes = Institute.query({filter: 'instempcount-is-null'});
        $q.all([$scope.instEmpCount.$promise, $scope.institutes.$promise]).then(function() {
            if (!$scope.instEmpCount.institute.id) {
                return $q.reject();
            }
            return Institute.get({id : $scope.instEmpCount.institute.id}).$promise;
        }).then(function(institute) {
            $scope.institutes.push(institute);
        });
        $scope.load = function(id) {
            InstEmpCount.get({id : id}, function(result) {
                $scope.instEmpCount = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmpCountUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('instGenInfo.instEmpCount');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            $state.go('instGenInfo.instEmpCount');
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmpCount.id != null) {
                InstEmpCount.update($scope.instEmpCount, onSaveSuccess, onSaveError);
            } else {
                console.log($scope.instEmpCount.institute);
                InstEmpCount.save($scope.instEmpCount, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
        $scope.instEmpCount = null;
        };
}]);
