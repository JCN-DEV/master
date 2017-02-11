'use strict';

angular.module('stepApp').controller('AssetDistributionDialogController',
    ['$rootScope', '$scope', '$stateParams', 'entity', 'AssetDistribution', '$state', 'HrEmployeeInfo', 'AssetRecord', 'ParseLinks', 'HrEmployeeInfoByEmployeeId', 'assetRequisitionsByRefId',
        function ($rootScope, $scope, $stateParams, entity, AssetDistribution, $state, HrEmployeeInfo, AssetRecord, ParseLinks, HrEmployeeInfoByEmployeeId, assetRequisitionsByRefId) {

            $scope.employeeGlobal = $rootScope.assetRefId;
            console.log('REF ID: '+$rootScope.assetRefId);
            $scope.assetRecord = {};
            $scope.assetDistribution = entity;
            //$scope.assetDistribution.transferRef = $rootScope.assetRefId;

            $scope.assetRequisition = [];

            /* HRM Employee */
            $scope.hrmEmployee = [];
            $scope.hrmEmployees = [];
            $scope.getHrmEmployee = [];

            //$scope.hrEmployeeInfo = HrEmployeeInfo.query();

            if($scope.employeeGlobal != null){
                assetRequisitionsByRefId.get({refId: $scope.employeeGlobal}, function (result, headers) {
                   // $scope.links = ParseLinks.parse(headers('link'));
                    $scope.assetRequisition = result;

                    $scope.assetDistribution.requisitonId = $rootScope.assetRefId;
                    $scope.assetDistribution.transferRef = $scope.assetRequisition.requisitionId;
                    $scope.assetDistribution.requestedQuantity = $scope.assetRequisition.quantity;
                    //$scope.assetRequisition.assetCode = $scope.assetRequisition
                    //$scope.getEmployee($scope.assetRequisition.empId);
                    //console.log('$scope.assetRequisition.empId' +$scope.assetRequisition.empId);

                    // Extra Codes

                    AssetRecord.get({id : $scope.assetRequisition.assetRecord.id}, function(result) {
                        $scope.assetRecord = result;
                        $scope.assetDistribution.assetRecord  = $scope.assetRecord;
                        $scope.assetDistribution.assetCode  = $scope.assetRecord.recordCode;
                        $scope.assetDistribution.assetName  = $scope.assetRecord.assetName;
                        $scope.assetDistribution.assetStatus  = $scope.assetRecord.status;

                    });

                    HrEmployeeInfoByEmployeeId.get({id: $scope.assetRequisition.empId}, function (result) {
                        $scope.assetDistribution.empId = $scope.assetRequisition.empId;
                        $scope.getHrmEmployee = result;
                        //$scope.assetDistribution.hrEmployeeInfo = $scope.getHrmEmployee;

                        $scope.assetDistribution.emploeeIds = $scope.assetRequisition.empId;
                        if ($scope.getHrmEmployee.fullName.toString() != null) {
                            $scope.assetDistribution.empName = $scope.getHrmEmployee.fullName;
                        } else {
                            $scope.assetDistribution.empName = 'Not Found';
                        }
                        if ($scope.getHrmEmployee.designationInfo.designationInfo != null) {
                            if ($scope.getHrmEmployee.designationInfo.designationInfo.designationName != null) {
                                $scope.assetDistribution.designation = $scope.getHrmEmployee.designationInfo.designationInfo.designationName;
                            }
                        } else {
                            $scope.assetDistribution.designation = 'Not Found';
                        }
                        if ($scope.getHrmEmployee.departmentInfo.departmentInfo != null) {
                            if ($scope.getHrmEmployee.departmentInfo.departmentInfo.departmentName != null) {
                                $scope.assetDistribution.department = $scope.getHrmEmployee.departmentInfo.departmentInfo.departmentName;
                            }
                        } else {
                            $scope.assetDistribution.department = 'Not Found';
                        }

                    });

                });
            }

            /*HrEmployeeInfo.query({page: $scope.page, size: 1000}, function (result, headers) {
                //$scope.links = ParseLinks.parse(headers('link'));
                $scope.hrmEmployee = result;
                $scope.hrmEmployee.forEach(function (data) {
                    //console.log("Application");u
                    //console.log(data.applicationId);
                    $scope.hrmEmployees.push(data.employeeId);
                });
                //console.log($scope.sisApplications);
            });*/

            $scope.getEmployee = function (id) {
                HrEmployeeInfoByEmployeeId.get({id: id}, function (result) {
                    $scope.getHrmEmployee = result;
                    $scope.assetDistribution.emploeeIds = $scope.assetRequisition.empId;
                    //$scope.assetDistribution.hrEmployeeInfo = $scope.getHrmEmployee;
                    //console.log($scope.sisStudentReg);
                    //if ($scope.getHrmEmployee.id != null) {
                    //    $scope.assetRequisition.empId = $scope.getHrmEmployee.empId;
                    //}
                    if ($scope.getHrmEmployee.fullName.toString() != null) {
                        $scope.assetDistribution.empName = $scope.getHrmEmployee.fullName;
                    } else {
                        $scope.assetDistribution.empName = 'Not Found';
                    }
                    if ($scope.getHrmEmployee.designationInfo.designationInfo != null) {
                        if ($scope.getHrmEmployee.designationInfo.designationInfo.designationName != null) {
                            $scope.assetDistribution.designation = $scope.getHrmEmployee.designationInfo.designationInfo.designationName;
                        }
                    } else {
                        $scope.assetDistribution.designation = 'Not Found';
                    }
                    if ($scope.getHrmEmployee.departmentInfo.departmentInfo != null) {
                        if ($scope.getHrmEmployee.departmentInfo.departmentInfo.departmentName != null) {
                            $scope.assetDistribution.department = $scope.getHrmEmployee.departmentInfo.departmentInfo.departmentName;
                        }
                    } else {
                        $scope.assetDistribution.department = 'Not Found';
                    }

                });
            }

            /*$scope.assetrecords = AssetRecord.query();
            $scope.load = function (id) {
                AssetDistribution.get({id: id}, function (result) {
                    $scope.assetDistribution = result;

                });
            };*/


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

//            $scope.ValueChange = function(id) {
//              angular.forEach($scope.hrEmployeeInfo, function (data) {
//
//              console.log(data.employeeId);
//
//                  if(id == data.employeeId){
//
//                      console.log('true');
//                  }
//
//              });
//            };


            $scope.AssetValueChange = function (CodeOfAsset) {
                angular.forEach($scope.assetrecords, function (code) {
                    if (CodeOfAsset == code.id) {
                        $scope.assetrecords.assetName = code.assetName;
                        $scope.assetrecords.assetStatus = code.status;
                    }
                })
                console.log($scope.assetrecords.assetStatus);
            };

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:assetDistributionUpdate', result);
                $state.go('assetDistribution');
               /* $state.go('assetDistribution', null, {reload: true}),
                    function () {
                        $state.go('assetDistribution');
                    };
*/
            };

            $scope.save = function () {

                if ($scope.assetDistribution.id != null) {

                    AssetDistribution.update($scope.assetDistribution, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.assetDistribution.updated');
                } else {

                    AssetDistribution.save($scope.assetDistribution, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.assetDistribution.created');
                    console.log($scope.assetDistribution);
                }
            };

            $scope.clear = function () {
                $state.go('assetDistribution');
            };


        }]);
