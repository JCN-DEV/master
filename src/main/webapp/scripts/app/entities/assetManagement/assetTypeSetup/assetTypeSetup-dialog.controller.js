'use strict';

angular.module('stepApp').controller('AssetTypeSetupDialogController',
    ['$scope','$rootScope', '$stateParams', 'entity','$state', 'AssetTypeSetup','$location',
    function ($scope, $rootScope, $stateParams, entity,$state, AssetTypeSetup,$location) {

        $scope.assetTypeSetup = entity;

        $scope.load = function (id) {
            AssetTypeSetup.get({id: id}, function (result) {
                $scope.assetTypeSetup = result;
                console.log($scope.assetTypeSetup);
            });
        };


        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:assetTypeSetupUpdate', result);
            $state.go('assetTypeSetup', null,{ reload: true }),
            function (){
                $state.go('assetTypeSetup');
            };
        }

        //    .result.then(function(result) {
        //    $state.go('assetRecord', null, { reload: true });
        //}, function() {
        //    $state.go('assetRecord');
        //});

        //$scope.load(1);

        $scope.save = function () {
            if ($scope.assetTypeSetup.id != null) {
                AssetTypeSetup.update($scope.assetTypeSetup, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.assetTypeSetup.updated');
            } else {
                AssetTypeSetup.save($scope.assetTypeSetup, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.assetTypeSetup.created');
            }

        };

        $scope.clear = function () {

        };
    }]);
