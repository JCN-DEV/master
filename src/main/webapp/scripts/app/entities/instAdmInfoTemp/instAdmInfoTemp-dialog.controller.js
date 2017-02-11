'use strict';

angular.module('stepApp').controller('InstAdmInfoTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'InstAdmInfoTemp', 'Institute',
        function($scope, $stateParams, $modalInstance, $q, entity, InstAdmInfoTemp, Institute) {

        $scope.instAdmInfoTemp = entity;
        $scope.institutes = Institute.query({filter: 'instadminfotemp-is-null'});
        $q.all([$scope.instAdmInfoTemp.$promise, $scope.institutes.$promise]).then(function() {
            if (!$scope.instAdmInfoTemp.institute.id) {
                return $q.reject();
            }
            return Institute.get({id : $scope.instAdmInfoTemp.institute.id}).$promise;
        }).then(function(institute) {
            $scope.institutes.push(institute);
        });
        $scope.load = function(id) {
            InstAdmInfoTemp.get({id : id}, function(result) {
                $scope.instAdmInfoTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instAdmInfoTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instAdmInfoTemp.id != null) {
                InstAdmInfoTemp.update($scope.instAdmInfoTemp, onSaveFinished);
            } else {
                InstAdmInfoTemp.save($scope.instAdmInfoTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
