'use strict';

angular.module('stepApp').controller('AssetRecordDialogController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AssetRecord', 'AssetCategorySetup','$state' ,'AssetTypeSetup', 'AssetSupplier', 'AssetAccuisitionSetup',
        function($scope, $rootScope, $stateParams, entity, AssetRecord, AssetCategorySetup,$state, AssetTypeSetup, AssetSupplier, AssetAccuisitionSetup) {

        $scope.assetRecord = entity;
        $scope.assetcategorysetups = AssetCategorySetup.query();
        $scope.assettypesetups = AssetTypeSetup.query();
        $scope.assetsuppliers = AssetSupplier.query();
        $scope.assetAccuisitionSetups = AssetAccuisitionSetup.query();


        $scope.load = function(id) {
            AssetRecord.get({id : id}, function(result) {
                $scope.assetRecord = result;

            });
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

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:assetRecordUpdate', result);
            $state.go('assetRecord', null,{ reload: true }),
                function (){
                    $state.go('assetRecord');
                };
        };

        $scope.save = function () {
            console.log('>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> '+$scope.assetRecord);
            if ($scope.assetRecord.id != null) {
                AssetRecord.update($scope.assetRecord, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.assetRecord.updated');
            } else {
                AssetRecord.save($scope.assetRecord, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.assetRecord.created');
            }
        };

        $scope.clear = function() {
        };

        $scope.filterAssetCatTypeSupplierByStatus = function () {
                                    return function (item) {
                                        if (item.status == true)
                                        {
                                            return true;
                                        }
                                        return false;
                                    };
                                };
}]);
