'use strict';

angular.module('stepApp').controller('InstAcaInfoTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'InstAcaInfoTemp', 'Institute',
        function($scope, $stateParams, $modalInstance, $q, entity, InstAcaInfoTemp, Institute) {

        $scope.instAcaInfoTemp = entity;
        $scope.institutes = Institute.query({filter: 'instacainfotemp-is-null'});
        $q.all([$scope.instAcaInfoTemp.$promise, $scope.institutes.$promise]).then(function() {
            if (!$scope.instAcaInfoTemp.institute.id) {
                return $q.reject();
            }
            return Institute.get({id : $scope.instAcaInfoTemp.institute.id}).$promise;
        }).then(function(institute) {
            $scope.institutes.push(institute);
        });
        $scope.load = function(id) {
            InstAcaInfoTemp.get({id : id}, function(result) {
                $scope.instAcaInfoTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instAcaInfoTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instAcaInfoTemp.id != null) {
                InstAcaInfoTemp.update($scope.instAcaInfoTemp, onSaveFinished);
            } else {
                InstAcaInfoTemp.save($scope.instAcaInfoTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
