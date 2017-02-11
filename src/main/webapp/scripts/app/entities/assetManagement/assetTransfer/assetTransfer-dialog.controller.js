'use strict';

angular.module('stepApp').controller('AssetTransferDialogController',
    ['$scope', '$rootScope', '$stateParams','entity', 'AssetTransfer','$state', 'Employee', 'AssetRecord','HrEmployeeInfoByEmployeeId',
        function($scope, $rootScope, $stateParams, entity, AssetTransfer,$state, Employee, AssetRecord, HrEmployeeInfoByEmployeeId) {

        $scope.assetTransfer = entity;
        $scope.employees = Employee.query();
        $scope.assetrecords = AssetRecord.query();
        $scope.load = function(id) {
            AssetTransfer.get({id : id}, function(result) {
                $scope.assetTransfer = result;
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
            $scope.$emit('stepApp:assetTransferUpdate', result);

            $state.go('assetTransfer', null,{ reload: true }),
                function (){
                    $state.go('assetTransfer');
                };


        };


            $scope.ValueChange = function(id) {
                angular.forEach($scope.employees, function (data) {
                    if(id == data.id){
                        $scope.assetDistribution.name = data.name;
                        $scope.assetDistribution.department = data.department;
                        $scope.assetDistribution.designation=data.designation;
                    }
                })

                console.log('adslkjfh');

            };

            $scope.ValueChangeTwo = function(code) {
                angular.forEach($scope.employees, function (data) {
                    if(code == data.id){
                        $scope.assetDistribution.na = data.name;
                        $scope.assetDistribution.department = data.department;
                        $scope.assetDistribution.designation=data.designation;
                    }
                })

                console.log('adslkjfh');

            };

            //$scope.assetRecordValue = function(codeAsset) {
            //    angular.forEach($scope.assetrecords, function (data) {
            //        if(codeAsset == data.recordCode){
            //            $scope.assetrecords.assetName = data.assetName;
            //
            //        }
            //    })
            //
            //    console.log(assetrecords.na);
            //
            //};


            $scope.AssetValueChange = function(CodeOfAsset){

                angular.forEach($scope.assetrecords, function(code){

                    if(CodeOfAsset == code.id){
                        $scope.assetrecords.assetName = code.assetName;
                        $scope.assetrecords.assetStatus = code.status;
                    }

                })
                console.log($scope.assetrecords.assetStatus);
            };





        $scope.save = function () {
            if ($scope.assetTransfer.id != null) {
                AssetTransfer.update($scope.assetTransfer, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.assetTransfer.updated');
            } else {
                AssetTransfer.save($scope.assetTransfer, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.assetTransfer.created');
            }
        };

        $scope.clear = function() {

        };
        $scope.getEmployee = function (id) {
                        HrEmployeeInfoByEmployeeId.get({id: id}, function (result) {
                            $scope.getHrmEmployee = result;
                            //$scope.AssetTransfer.emploeeIds = $scope.assetRequisition.empId;
                            //$scope.assetDistribution.hrEmployeeInfo = $scope.getHrmEmployee;
                            //console.log($scope.sisStudentReg);
                            //if ($scope.getHrmEmployee.id != null) {
                            //    $scope.assetRequisition.empId = $scope.getHrmEmployee.empId;
                            //}
                            if ($scope.getHrmEmployee.fullName.toString() != null) {
                                $scope.AssetTransfer.empName = $scope.getHrmEmployee.fullName;
                            } else {
                                $scope.AssetTransfer.empName = 'Not Found';
                            }
                            if ($scope.getHrmEmployee.designationInfo.designationInfo != null) {
                                if ($scope.getHrmEmployee.designationInfo.designationInfo.designationName != null) {
                                    $scope.AssetTransfer.designation = $scope.getHrmEmployee.designationInfo.designationInfo.designationName;
                                }
                            } else {
                                $scope.AssetTransfer.designation = 'Not Found';
                            }
                            if ($scope.getHrmEmployee.departmentInfo.departmentInfo != null) {
                                if ($scope.getHrmEmployee.departmentInfo.departmentInfo.departmentName != null) {
                                    $scope.AssetTransfer.department = $scope.getHrmEmployee.departmentInfo.departmentInfo.departmentName;
                                }
                            } else {
                                $scope.AssetTransfer.department = 'Not Found';
                            }

                        });
                    }

}]);
