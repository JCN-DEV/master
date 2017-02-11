'use strict';

angular.module('stepApp').controller('AssetRepairDialogController',
    ['$scope', '$rootScope','$stateParams','entity','AssetRepair','$state','AssetRecord','Employee','AssetDistribution',
        function($scope, $rootScope, $stateParams, entity, AssetRepair,$state, AssetRecord, Employee, AssetDistribution) {
        $scope.AssetValueChange={};
        $scope.assetRepair = entity;
        $scope.assetRepairInfo = {};
        /*$scope.assetRecordDetails={};*/
        $scope.employees = Employee.query();
        $scope.assetdistributions = AssetDistribution.query();
        $scope.assetRecords = AssetRecord.query();
        $scope.load = function(id) {
            AssetRepair.get({id : id}, function(result) {
                $scope.assetRepair = result;
            });
            /*AssetRecord.get({id: id}, function (result) {
                $scope.assetRecordDetails = result;
            });*/
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:assetRepairUpdate', result);

            $state.go('assetRepair', null,{ reload: true }),
                function (){
                    $state.go('assetRepair');
                };
        };

            $scope.getAssetRecords = function () {
                AssetRecord.get({id: $scope.AssetRepair.assetCode.id}, function (result) {
                    console.log(result);
                    $scope.assetRecordDetails = result;
                    $scope.assetRepair.assetName = result.assetName;

                });
            }
            //retrieve asset information by asset code


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

                angular.forEach($scope.assetdistributions, function(code){

                    if(CodeOfAsset == code.id){
                        $scope.assetdistributions.assetNam = code.assetRecord.assetName;
                        $scope.assetdistributions.purchaseDate = code.assetRecord.purchaseDate;
                        $scope.assetdistributions.vendorName = code.assetRecord.vendorName;
                        $scope.assetdistributions.empName = code.employee.name;
                        $scope.assetdistributions.empDepartment = code.employee.department;


                        //$scope.assetrecords.purchaseDate = code.assetRecord.purchaseDate;
                        //$scope.assetrecords.vendorName = code.assetRecord.vendorName;
                        //$scope.assetrecords.assetStatus = code.assetRecord.status;
                        //$scope.employee.designation = code.employee.designation;

                    }

                })
                //console.log($scope.assetrecords.designation);
                console.log( $scope.assetdistributions.empName);
                console.log($scope.assetdistributions.empDepartment);
            };



            $scope.save = function () {

                console.log($scope.assetRepair);

            if ($scope.assetRepair.id != null) {
                AssetRepair.update($scope.assetRepair, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.assetRepair.updated');
            } else {
                AssetRepair.save($scope.assetRepair, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.assetRepair.created');
            }
        };

        $scope.clear = function() {

        };
}]);
