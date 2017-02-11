'use strict';

angular.module('stepApp').controller('AssetCategorySetupDialogController',
['$scope', '$rootScope', '$stateParams', 'entity', 'AssetCategorySetup','$state', 'AssetTypeSetup',
        function($scope, $rootScope, $stateParams, entity, AssetCategorySetup,$state, AssetTypeSetup) {

        $scope.message = '';
        $scope.assetCategorySetup = {};
        $scope.assetCategorySetup.status = true;

        AssetCategorySetup.get({id : $stateParams.id},function(result) {
            $scope.assetCategorySetup = result;
            if($scope.assetCategorySetup == null ){
                $scope.typeCode = '';
            }
            else {
                console.log($scope.assetCategorySetup);
                $scope.typeCode = $scope.assetCategorySetup.assetTypeSetup.typeCode;
            }
        });

        console.log($scope.assetCategorySetup);
        $scope.assettypesetups = AssetTypeSetup.query();
        $scope.load = function(id) {
            AssetCategorySetup.get({id : id}, function(result) {
                $scope.assetCategorySetup = result;
            });
        };

        var onSaveFinished = function (result) {

            $scope.$emit('stepApp:assetCategorySetupUpdate', result);
            $state.go('assetCategorySetup', null,{ reload: true }),
                function (){
                    $state.go('assetCategorySetup');
                };

        };

        $scope.save = function () {


            if ($scope.assetCategorySetup.id != null) {
                AssetCategorySetup.update($scope.assetCategorySetup, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.assetCategorySetup.updated');
            } else {

                AssetCategorySetup.save($scope.assetCategorySetup, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.assetCategorySetup.created');
            }

            /*AssetTypeSetupByCode.get({code: $scope.typeCode}, function () {//assetTypeSetup

                    //$scope.assetCategorySetup.assetTypeSetup = assetTypeSetup;

                },
                function (response) {
                    //if (response.status === 404) {
                        $scope.message = 'Invalid type code provided';
                    //}
                }
            );*/
        };

        $scope.clear = function() {

        };
}]);
