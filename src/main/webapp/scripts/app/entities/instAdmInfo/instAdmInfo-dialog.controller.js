'use strict';

angular.module('stepApp').controller('InstAdmInfoDialogController',
    ['$scope', '$stateParams', '$q', 'entity', 'InstAdmInfo', 'Institute',
        function($scope, $stateParams, $q, entity, InstAdmInfo, Institute) {

        $scope.instAdmInfo = entity;
        $scope.institutes = Institute.query({filter: 'instadminfo-is-null'});
        $q.all([$scope.instAdmInfo.$promise, $scope.institutes.$promise]).then(function() {
            if (!$scope.instAdmInfo.institute.id) {
                return $q.reject();
            }
            return Institute.get({id : $scope.instAdmInfo.institute.id}).$promise;
        }).then(function(institute) {
            $scope.institutes.push(institute);
        });
        $scope.load = function(id) {
            InstAdmInfo.get({id : id}, function(result) {
                $scope.instAdmInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instAdmInfoUpdate', result);

            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instAdmInfo.id != null) {
                InstAdmInfo.update($scope.instAdmInfo, onSaveSuccess, onSaveError);
            } else {
                InstAdmInfo.save($scope.instAdmInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
        $scope.instAdmInfo = null;
        };
}]);
