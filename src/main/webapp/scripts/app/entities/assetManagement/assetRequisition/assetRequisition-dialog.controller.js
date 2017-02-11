'use strict';

angular.module('stepApp').controller('AssetRequisitionDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'AssetRequisition', 'AssetTypeSetup', 'AssetCategorySetup', 'AssetRecord', 'HrEmployeeInfo', 'ParseLinks', 'HrEmployeeInfoByEmployeeId', 'AssetCategorySetupByTypeId', 'AssetRecordByTypeIdAndCategoryId',
        function ($scope, $rootScope, $stateParams, $state, entity, AssetRequisition, AssetTypeSetup, AssetCategorySetup, AssetRecord, HrEmployeeInfo, ParseLinks, HrEmployeeInfoByEmployeeId, AssetCategorySetupByTypeId, AssetRecordByTypeIdAndCategoryId) {

            $scope.assetRequisition = entity;
            $scope.hrEmployeeInfos = [];
            $scope.assetcategorysetups = AssetCategorySetup.query();
            //$scope.assettypesetups = {};
            //$scope.assetrecords = {};
            $scope.assetDetails = [];
            $scope.assettypesetups = AssetTypeSetup.query();
            //$scope.assetcategorysetups = AssetCategorySetup.query();
            //$scope.assetrecords = AssetRecord.query();

            /* HRM Employee */
            $scope.hrmEmployee = [];
            $scope.hrmEmployees = [];
            $scope.getHrmEmployee = [];

            HrEmployeeInfo.query({page: $scope.page, size: 1000}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.hrmEmployee = result;
                $scope.hrmEmployee.forEach(function (data) {
                    //console.log("Application");
                    //console.log(data.applicationId);
                    $scope.hrmEmployees.push(data.employeeId);
                });
                //console.log($scope.sisApplications);
            });

            /*AssetCategorySetup.query({page: $scope.page, size: 1000}, function (result, headers) {
             $scope.links = ParseLinks.parse(headers('link'));
             $scope.assetCategorys = result;
             });*/

            $scope.load = function (id) {
                AssetRequisition.get({id: id}, function (result) {
                    $scope.assetRequisition = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:assetRequisitionUpdate', result);
                $state.go('assetRequisition');
            };

            $scope.getAssetTypes = function (id) {
                //$scope.assettypesetups = AssetTypeSetup.query();
            }

            $scope.getAssetRecords = function (catId, typeId) {
                AssetRecordByTypeIdAndCategoryId.query({typeId: typeId, categoryId: catId}, function (result) {
                    $scope.assetrecords = result;
                });
            }

            $scope.getAssetCategories = function (typeId) {
                AssetCategorySetupByTypeId.query({id: typeId}, function (result) {
                    $scope.assetcategorysetups = result;
                });
            }

            $scope.getEmployee = function (id) {
                HrEmployeeInfoByEmployeeId.get({id: id}, function (result) {
                    $scope.getHrmEmployee = result;
                    //console.log($scope.sisStudentReg);
                    //if ($scope.getHrmEmployee.id != null) {
                    //    $scope.assetRequisition.empId = $scope.getHrmEmployee.empId;
                    //}
                    if ($scope.getHrmEmployee.fullName.toString() != null) {
                        $scope.assetRequisition.empName = $scope.getHrmEmployee.fullName;
                    } else {
                        $scope.assetRequisition.empName = 'Not Found';
                    }
                    if ($scope.getHrmEmployee.designationInfo.designationInfo != null) {
                        if ($scope.getHrmEmployee.designationInfo.designationInfo.designationName != null) {
                            $scope.assetRequisition.designation = $scope.getHrmEmployee.designationInfo.designationInfo.designationName;
                        }
                    } else {
                        $scope.assetRequisition.designation = 'Not Found';
                    }
                    if ($scope.getHrmEmployee.departmentInfo.departmentInfo != null) {
                        if ($scope.getHrmEmployee.departmentInfo.departmentInfo.departmentName != null) {
                            $scope.assetRequisition.department = $scope.getHrmEmployee.departmentInfo.departmentInfo.departmentName;
                        }
                    } else {
                        $scope.assetRequisition.department = 'Not Found';
                    }

                });
            }

            $scope.getAssetDetails = function (id) {
                AssetRecord.get({id: id}, function (result) {
                    $scope.assetDetails = result;
                    console.log($scope.assetDetails);
                    if ($scope.assetDetails.recordCode != null) {
                        $scope.assetRequisition.assetCode = $scope.assetDetails.recordCode;
                    } else {
                        $scope.assetRequisition.assetCode = 'Not Found';
                    }
                    /*if($scope.getHrmEmployee.designationInfo.designationInfo.designationName != null){
                     $scope.assetRequisition.available = $scope.getHrmEmployee.designationInfo.designationInfo.designationName;
                     } else {
                     $scope.assetRequisition.available = 'Not Found';
                     }*/
                    $scope.assetRequisition.available = 'Yes';
                    $scope.assetRequisition.assetStatus = $scope.assetDetails.status ? 'Active' : 'Inactive';

                });
            }

            $scope.save = function () {
                console.log($scope.assetRequisition);
                if ($scope.assetRequisition.id != null) {
                    AssetRequisition.update($scope.assetRequisition, onSaveFinished);
                    $rootScope.setWarningMessage('You have successfully request for an asset.');
                } else {
                    AssetRequisition.save($scope.assetRequisition, onSaveFinished);
                    $rootScope.setSuccessMessage('You have successfully request for an asset.');
                }
            };

            $scope.clear = function () {
                $state.go('assetRequisition');
            };
        }]);
