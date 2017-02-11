'use strict';

angular.module('stepApp').controller('AssetAuctionInformationDialogController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AssetAuctionInformation', '$state', 'AssetDistribution','AssetRecord',
        function ($scope, $rootScope, $stateParams, entity, AssetAuctionInformation, $state, AssetDistribution,AssetRecord) {

            $scope.assetAuctionInformation = {};
            $scope.assetdistributions = AssetDistribution.query();
            $scope.assetRecords = AssetRecord.query();
            if($stateParams.id) {
                AssetAuctionInformation.get({id: $stateParams.id}, function (result) {
                    $scope.assetAuctionInformation = result;
                    //console.log($scope.assetAuctionInformation.assetDistribution.id);

                });
            }

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:assetAuctionInformationUpdate', result);

                $state.go('assetAuctionInformation', null, {reload: true}),
                    function () {
                        $state.go('assetAuctionInformation');
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


            $scope.AssetValueChange = function (CodeOfAsset) {

                angular.forEach($scope.assetdistributions, function (code) {

                    if (CodeOfAsset == code.id) {
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
                //console.log($scope.assetdistributions.empName);
                //console.log($scope.assetdistributions.empDepartment);
            };

            //$scope.AssetValueChange = function(CodeOfAsset){
            //
            //    angular.forEach($scope.assetdistributions, function(code){
            //
            //        if(CodeOfAsset == code.assetRecord.id){
            //            $scope.assetdistributions.assetName = code.assetRecord.assetName;
            //            $scope.assetdistributions.purchaseDate = code.assetRecord.purchaseDate;
            //            $scope.assetdistributions.usedBy = code.employee.name;
            //            $scope.assetdistributions.usedByDept = code.employee.department;
            //        }
            //        console.log($scope.assetdistributions.usedBy);
            //    })
            //
            //};
            //$scope.AssetValueChange();


            $scope.save = function () {
                if ($scope.assetAuctionInformation.id != null) {
                    AssetAuctionInformation.update($scope.assetAuctionInformation, onSaveFinished);
                    $rootScope.setErrorMessage('stepApp.assetAuctionInformation.deleted');
                } else {
                    AssetAuctionInformation.save($scope.assetAuctionInformation, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.assetAuctionInformation.created');
                }
            };

            $scope.clear = function () {

            };
        }]);
