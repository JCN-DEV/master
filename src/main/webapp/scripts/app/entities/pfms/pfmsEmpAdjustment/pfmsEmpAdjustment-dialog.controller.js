'use strict';

angular.module('stepApp').controller('PfmsEmpAdjustmentDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity','PfmsEmpMembership', 'PfmsEmpMembershipForm', 'PfmsEmpAdjustment', 'User', 'Principal', 'DateUtils','HrEmployeeInfo',
        function($scope, $rootScope, $stateParams, $state, entity, PfmsEmpMembership,PfmsEmpMembershipForm,  PfmsEmpAdjustment, User, Principal, DateUtils, HrEmployeeInfo) {

            $scope.pfmsEmpAdjustment = entity;
            //$scope.hremployeeinfos = HrEmployeeInfo.query();
            $scope.hremployeeinfos = [];
            $scope.pfmsEmpMembershipForms = [];

            PfmsEmpMembershipForm.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.pfmsEmpMembershipForms.push(dtoInfo);
                        $scope.hremployeeinfos.push(dtoInfo.employeeInfo);
                    }
                });
            });

            if($stateParams.id){
                PfmsEmpAdjustment.get({id : $stateParams.id}, function(result) {
                    $scope.pfmsEmpAdjustment= result;
                    $scope.loadEmployeeInfo();
                });

            }



            $scope.loadEmployeeInfo = function(){
                $scope.empInfo = $scope.pfmsEmpAdjustment.employeeInfo;
                $scope.designationName          = $scope.empInfo.designationInfo.designationInfo.designationName;
                $scope.departmentName           = $scope.empInfo.departmentInfo.departmentInfo.departmentName;
                $scope.dutySide                 = $scope.empInfo.workArea.typeName;
                $scope.nationality              = $scope.empInfo.nationality;
                $scope.fatherName               = $scope.empInfo.fatherName;
            };

            $scope.loggedInUser =   {};
            $scope.getLoggedInUser = function ()
            {
                Principal.identity().then(function (account)
                {
                    User.get({login: account.login}, function (result)
                    {
                        $scope.loggedInUser = result;
                    });
                });
            };
            $scope.getLoggedInUser();

            $scope.onChangeIsCurrentBalance = function(){
                $scope.pfmsEmpAdjustment.ownContribute = 0;
                $scope.pfmsEmpAdjustment.preOwnContribute = 0;
                $scope.pfmsEmpAdjustment.ownContributeInt = 0;
                $scope.pfmsEmpAdjustment.preOwnContributeInt = 0;
                $scope.pfmsEmpMembership = null;
                PfmsEmpMembership.query(function (result) {
                    angular.forEach(result, function (dtoInfo) {
                        if (dtoInfo.employeeInfo.id == $scope.pfmsEmpAdjustment.employeeInfo.id) {
                            if ($scope.pfmsEmpAdjustment.isCurrentBalance == true) {
                                $scope.pfmsEmpAdjustment.ownContribute          = dtoInfo.curOwnContribute;
                                $scope.pfmsEmpAdjustment.preOwnContribute       = dtoInfo.curOwnContribute;
                                $scope.pfmsEmpAdjustment.ownContributeInt       = dtoInfo.curOwnContributeInt;
                                $scope.pfmsEmpAdjustment.preOwnContributeInt    = dtoInfo.curOwnContributeInt;
                            } else if ($scope.pfmsEmpAdjustment.isCurrentBalance == false){
                                $scope.pfmsEmpAdjustment.ownContribute          = dtoInfo.initOwnContribute;
                                $scope.pfmsEmpAdjustment.preOwnContribute       = dtoInfo.initOwnContribute;
                                $scope.pfmsEmpAdjustment.ownContributeInt       = dtoInfo.initOwnContributeInt;
                                $scope.pfmsEmpAdjustment.preOwnContributeInt    = dtoInfo.initOwnContributeInt;
                            }
                            $scope.pfmsEmpMembership = dtoInfo;
                        }

                    });
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:pfmsEmpAdjustmentUpdate', result);
                $scope.isSaving = false;
                if ($scope.pfmsEmpMembership) {
                    if ($scope.pfmsEmpAdjustment.isCurrentBalance == true) {
                        $scope.pfmsEmpMembership.curOwnContribute       = $scope.pfmsEmpAdjustment.ownContribute;
                        $scope.pfmsEmpMembership.curOwnContributeInt    = $scope.pfmsEmpAdjustment.ownContributeInt;
                        $scope.pfmsEmpMembership.curOwnContributeTot    = $scope.pfmsEmpMembership.curOwnContribute + $scope.pfmsEmpMembership.curOwnContributeInt;
                    }else{
                        $scope.pfmsEmpMembership.initOwnContribute      = $scope.pfmsEmpAdjustment.ownContribute;
                        $scope.pfmsEmpMembership.initOwnContributeInt   = $scope.pfmsEmpAdjustment.ownContributeInt;
                    }
                    $scope.pfmsEmpMembership.updateBy               = $scope.loggedInUser.id;
                    $scope.pfmsEmpMembership.updateDate             = DateUtils.convertLocaleDateToServer(new Date());
                    PfmsEmpMembership.update($scope.pfmsEmpMembership);
                }
                $state.go("pfmsEmpAdjustment");
            };

            $scope.save = function () {
                $scope.pfmsEmpAdjustment.updateBy = $scope.loggedInUser.id;
                $scope.pfmsEmpAdjustment.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.pfmsEmpAdjustment.id != null) {
                    PfmsEmpAdjustment.update($scope.pfmsEmpAdjustment, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.pfmsEmpAdjustment.updated');

                } else {
                    $scope.pfmsEmpAdjustment.createBy = $scope.loggedInUser.id;
                    $scope.pfmsEmpAdjustment.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    PfmsEmpAdjustment.save($scope.pfmsEmpAdjustment, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsEmpAdjustment.created');

                }
            };



        }]);


