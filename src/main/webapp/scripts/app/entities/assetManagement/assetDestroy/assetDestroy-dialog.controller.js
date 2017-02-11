'use strict';

angular.module('stepApp').controller('AssetDestroyDialogController',
    ['$scope','$state', '$stateParams', '$q', 'entity', 'AssetDestroy', 'AssetDistribution', 'AssetRecord',
        function($scope,$state, $stateParams, $q, entity, AssetDestroy, AssetDistribution, AssetRecord) {
        $scope.assetDestroy =entity;
        /*$scope.assetDistributions = AssetDistribution.query({filter: 'assetdestroy-is-null'});*/
        $scope.assetRecords=AssetRecord.query();
        if($stateParams.id) {
            AssetDestroy.get({id: $stateParams.id}, function (result) {
                $scope.assetDestroy = result;
            });
        }

        $scope.assetrecords = AssetRecord.query();
        $scope.load = function(id) {
            AssetDestroy.get({id : id}, function(result) {
                $scope.assetDestroy = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:assetDestroyUpdate', result);
            $state.go('assetDestroy', null,{ reload: true }),
                function (){
                    $state.go('assetDestroy');
                };
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



            $scope.AssetValueChange = function(CodeOfAsset){

                angular.forEach($scope.assetDistributions, function(code){

                    if(CodeOfAsset == code.id){
                        $scope.assetDestroy.assetDistribution.assetNam = code.assetRecord.assetName;
                        $scope.assetDestroy.assetDistribution.purchaseDate = code.assetRecord.purchaseDate;
                        $scope.assetDestroy.assetDistribution.vendorName = code.assetRecord.vendorName;
                        $scope.assetDestroy.assetDistribution.empName = code.employee.name;
                        $scope.assetDestroy.assetDistribution.empDepartment = code.employee.department;


                        //$scope.assetrecords.purchaseDate = code.assetRecord.purchaseDate;
                        //$scope.assetrecords.vendorName = code.assetRecord.vendorName;
                        //$scope.assetrecords.assetStatus = code.assetRecord.status;
                        //$scope.employee.designation = code.employee.designation;

                    }

                })
                //console.log($scope.assetrecords.designation);
                console.log( $scope.assetDestroy.assetDistribution.empName);
                console.log($scope.assetDestroy.assetDistribution.empDepartment);
            };

            $scope.save = function () {
                console.log($scope.assetDestroy);
            if ($scope.assetDestroy.id != null) {
                AssetDestroy.update($scope.assetDestroy, onSaveFinished);
            } else {
                AssetDestroy.save($scope.assetDestroy, onSaveFinished);
            }
        };

        $scope.clear = function() {

        };
}]);
