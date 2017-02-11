'use strict';

angular.module('stepApp').controller('InstInfraInfoDialogController',
    ['$scope', '$stateParams', '$state', '$q',  'InstInfraInfo', 'Institute', 'InstBuilding', 'InstLand',
        function($scope, $stateParams, $state, $q, InstInfraInfo, Institute, InstBuilding, InstLand) {

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
        //$scope.instInfraInfo = InstInfraInfo.get({id : $stateParams.id});;

        /*$scope.institutes = Institute.query({filter: 'instinfrainfo-is-null'});*/

        /*$q.all([$scope.instInfraInfo.$promise, $scope.institutes.$promise]).then(function() {
            if (!$scope.instInfraInfo.institute.id) {
                return $q.reject();
            }
            return Institute.get({id : $scope.instInfraInfo.institute.id}).$promise;
        }).then(function(institute) {
            $scope.institutes.push(institute);
        });*/

        /*$scope.instbuildings = InstBuilding.query({filter: 'instinfrainfo-is-null'});*/

        /*$q.all([$scope.instInfraInfo.$promise, $scope.instbuildings.$promise]).then(function() {
            if (!$scope.instInfraInfo.instBuilding.id) {
                return $q.reject();
            }
            return InstBuilding.get({id : $scope.instInfraInfo.instBuilding.id}).$promise;
        }).then(function(instBuilding) {
            $scope.instbuildings.push(instBuilding);
        });*/


        /*$scope.instlands = InstLand.query({filter: 'instinfrainfo-is-null'});*/

        /*$q.all([$scope.instInfraInfo.$promise, $scope.instlands.$promise]).then(function() {
            if (!$scope.instInfraInfo.instLand.id) {
                return $q.reject();
            }
            return InstLand.get({id : $scope.instInfraInfo.instLand.id}).$promise;
        }).then(function(instLand) {
            $scope.instlands.push(instLand);
        });*/

        $scope.load = function(id) {
            InstInfraInfo.get({id : id}, function(result) {
                $scope.instInfraInfo = result;
                $scope.instLand = $scope.instInfraInfo.instLand;
                $scope.instBuilding = $scope.instInfraInfo.instBuilding;
            });
        };
            $scope.load($stateParams.id);

            var onInstBuildingSaveSuccess = function (result) {
            $scope.$emit('stepApp:instInfraInfoUpdate', result);
            $scope.isSaving = false;
            $scope.instInfraInfo.instBuilding = result;
            InstLand.save($scope.instLand, onInstLandSaveSuccess, onInstLandSaveError);
        };
        var onInstLandSaveSuccess = function (result) {
            $scope.$emit('stepApp:instInfraInfoUpdate', result);
            $scope.isSaving = false;
            $scope.instInfraInfo.instLand = result;
            InstInfraInfo.save($scope.instInfraInfo, onInstInfraInfoSaveSuccess, onInstInfraInfoSaveError);
        };
        var onInstInfraInfoSaveSuccess = function (result) {
            $scope.$emit('stepApp:instInfraInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('instGenInfo.instInfraInfo');
        };

        var onInstBuildingSaveError = function (result) {
            $scope.isSaving = false;
        };
        var onInstLandSaveError = function (result) {
            $scope.isSaving = false;
        };

        var onInstInfraInfoSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instInfraInfo.id != null) {
                InstBuilding.update($scope.instBuilding, onInstBuildingSaveSuccess, onInstBuildingSaveError);
            } else {
                $scope.instInfraInfo.status = 0;
                InstBuilding.save($scope.instBuilding, onInstBuildingSaveSuccess, onInstBuildingSaveError);
            }
        };

        $scope.clear = function() {
        };
}]);
