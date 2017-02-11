'use strict';

angular.module('stepApp').controller('InstInfraInfoTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'InstInfraInfoTemp', 'Institute', 'InstBuilding', 'InstLand',
        function($scope, $stateParams, $modalInstance, $q, entity, InstInfraInfoTemp, Institute, InstBuilding, InstLand) {

        $scope.instInfraInfoTemp = entity;
        $scope.institutes = Institute.query({filter: 'instinfrainfotemp-is-null'});
        $q.all([$scope.instInfraInfoTemp.$promise, $scope.institutes.$promise]).then(function() {
            if (!$scope.instInfraInfoTemp.institute.id) {
                return $q.reject();
            }
            return Institute.get({id : $scope.instInfraInfoTemp.institute.id}).$promise;
        }).then(function(institute) {
            $scope.institutes.push(institute);
        });
        $scope.instbuildings = InstBuilding.query({filter: 'instinfrainfotemp-is-null'});
        $q.all([$scope.instInfraInfoTemp.$promise, $scope.instbuildings.$promise]).then(function() {
            if (!$scope.instInfraInfoTemp.instBuilding.id) {
                return $q.reject();
            }
            return InstBuilding.get({id : $scope.instInfraInfoTemp.instBuilding.id}).$promise;
        }).then(function(instBuilding) {
            $scope.instbuildings.push(instBuilding);
        });
        $scope.instlands = InstLand.query({filter: 'instinfrainfotemp-is-null'});
        $q.all([$scope.instInfraInfoTemp.$promise, $scope.instlands.$promise]).then(function() {
            if (!$scope.instInfraInfoTemp.instLand.id) {
                return $q.reject();
            }
            return InstLand.get({id : $scope.instInfraInfoTemp.instLand.id}).$promise;
        }).then(function(instLand) {
            $scope.instlands.push(instLand);
        });
        $scope.load = function(id) {
            InstInfraInfoTemp.get({id : id}, function(result) {
                $scope.instInfraInfoTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instInfraInfoTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instInfraInfoTemp.id != null) {
                InstInfraInfoTemp.update($scope.instInfraInfoTemp, onSaveFinished);
            } else {
                InstInfraInfoTemp.save($scope.instInfraInfoTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
