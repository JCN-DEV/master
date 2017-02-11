'use strict';

angular.module('stepApp').controller('InstituteInfraInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'InstituteInfraInfo', 'Institute', 'InstBuilding', 'InstLand',
        function($scope, $stateParams, $modalInstance, $q, entity, InstituteInfraInfo, Institute, InstBuilding, InstLand) {

        $scope.instituteInfraInfo = entity;
        $scope.institutes = Institute.query({filter: 'instituteinfrainfo-is-null'});
        $q.all([$scope.instituteInfraInfo.$promise, $scope.institutes.$promise]).then(function() {
            if (!$scope.instituteInfraInfo.institute.id) {
                return $q.reject();
            }
            return Institute.get({id : $scope.instituteInfraInfo.institute.id}).$promise;
        }).then(function(institute) {
            $scope.institutes.push(institute);
        });
        $scope.instbuildings = InstBuilding.query({filter: 'instituteinfrainfo-is-null'});
        $q.all([$scope.instituteInfraInfo.$promise, $scope.instbuildings.$promise]).then(function() {
            if (!$scope.instituteInfraInfo.instBuilding.id) {
                return $q.reject();
            }
            return InstBuilding.get({id : $scope.instituteInfraInfo.instBuilding.id}).$promise;
        }).then(function(instBuilding) {
            $scope.instbuildings.push(instBuilding);
        });
        $scope.instlands = InstLand.query({filter: 'instituteinfrainfo-is-null'});
        $q.all([$scope.instituteInfraInfo.$promise, $scope.instlands.$promise]).then(function() {
            if (!$scope.instituteInfraInfo.instLand.id) {
                return $q.reject();
            }
            return InstLand.get({id : $scope.instituteInfraInfo.instLand.id}).$promise;
        }).then(function(instLand) {
            $scope.instlands.push(instLand);
        });
        $scope.load = function(id) {
            InstituteInfraInfo.get({id : id}, function(result) {
                $scope.instituteInfraInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instituteInfraInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instituteInfraInfo.id != null) {
                InstituteInfraInfo.update($scope.instituteInfraInfo, onSaveSuccess, onSaveError);
            } else {
                InstituteInfraInfo.save($scope.instituteInfraInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
