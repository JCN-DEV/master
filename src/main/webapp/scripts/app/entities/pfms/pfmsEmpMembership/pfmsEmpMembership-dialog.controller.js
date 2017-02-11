'use strict';

angular.module('stepApp').controller('PfmsEmpMembershipDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'PfmsEmpMembership','PfmsEmpMembershipForm', 'User', 'Principal', 'DateUtils','HrEmployeeInfo',
        function($scope, $rootScope, $stateParams, $state, entity, PfmsEmpMembership, PfmsEmpMembershipForm, User, Principal, DateUtils, HrEmployeeInfo) {

            $scope.pfmsEmpMembership = entity;
            $scope.hremployeeinfos = [];
            $scope.pfmsEmpMembershipForms = [];
            $scope.pfmsEmpMembershipOne = entity;

            PfmsEmpMembershipForm.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.pfmsEmpMembershipForms.push(dtoInfo);
                        $scope.hremployeeinfos.push(dtoInfo.employeeInfo);
                    }
                });
            });

            if($stateParams.id){
                PfmsEmpMembership.get({id : $stateParams.id}, function(result) {
                    $scope.pfmsEmpMembership = result;
                    $scope.loadEmployeeInfo();
                });

            }

            $scope.loadPfmsEmpMembership = function(){
                PfmsEmpMembership.get({id : $stateParams.id}, function(result) {
                    $scope.pfmsEmpMembershipOne = result;
                });
            };

            $scope.isExitsData = true;
            $scope.duplicateCheckMember = function(){
                $scope.isExitsData = true;
                if($stateParams.id){
                    $scope.loadPfmsEmpMembership();
                    if($scope.pfmsEmpMembership.employeeInfo.id != $scope.pfmsEmpMembershipOne.employeeInfo.id){
                        PfmsEmpMembership.query(function(result){
                            angular.forEach(result,function(dtoInfo){
                                if(dtoInfo.employeeInfo.id == $scope.pfmsEmpMembership.employeeInfo.id){
                                    $scope.isExitsData = false;
                                }
                            });
                        });
                    }

                }else{
                    PfmsEmpMembership.query(function(result){
                        angular.forEach(result,function(dtoInfo){
                            if(dtoInfo.employeeInfo.id == $scope.pfmsEmpMembership.employeeInfo.id){
                                $scope.isExitsData = false;
                            }
                        });
                    });
                }

            };

            $scope.curContributeChange = function(){
                $scope.pfmsEmpMembership.curOwnContributeTot = $scope.pfmsEmpMembership.curOwnContribute + $scope.pfmsEmpMembership.curOwnContributeInt;
            };

            $scope.loadEmployeeInfo = function(){
                $scope.empInfo = $scope.pfmsEmpMembership.employeeInfo;
                $scope.designationName          = $scope.empInfo.designationInfo.designationInfo.designationName;
                $scope.departmentName           = $scope.empInfo.departmentInfo.departmentInfo.departmentName;
                $scope.dutySide                 = $scope.empInfo.workArea.typeName;
                $scope.nationality              = $scope.empInfo.nationality;
                $scope.fatherName               = $scope.empInfo.fatherName;
                $scope.duplicateCheckMember();
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

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:pfmsEmpMembershipUpdate', result);
                $scope.isSaving = false;
                $state.go("pfmsEmpMembership");
            };

            $scope.save = function () {
                $scope.pfmsEmpMembership.updateBy = $scope.loggedInUser.id;
                $scope.pfmsEmpMembership.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.pfmsEmpMembership.id != null) {
                    PfmsEmpMembership.update($scope.pfmsEmpMembership, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.pfmsEmpMembership.updated');
                } else {
                    $scope.pfmsEmpMembership.createBy = $scope.loggedInUser.id;
                    $scope.pfmsEmpMembership.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    PfmsEmpMembership.save($scope.pfmsEmpMembership, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsEmpMembership.created');

                }
            };

        }]);



