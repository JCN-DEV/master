'use strict';

angular.module('stepApp').controller('PfmsRegisterDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PfmsRegister','PfmsEmpMembershipForm', 'User', 'Principal', 'DateUtils','HrEmployeeInfo',
        function($scope, $rootScope, $stateParams, $state, entity, PfmsRegister, PfmsEmpMembershipForm,  User, Principal, DateUtils, HrEmployeeInfo) {

            $scope.pfmsRegister = entity;
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

            $scope.loadEmployeeInfo = function(){
                $scope.empInfo = $scope.pfmsRegister.employeeInfo;
                $scope.designationName          = $scope.empInfo.designationInfo.designationInfo.designationName;
                $scope.departmentName           = $scope.empInfo.departmentInfo.departmentInfo.departmentName;
                $scope.dutySide                 = $scope.empInfo.workArea.typeName;
                $scope.nationality              = $scope.empInfo.nationality;
                $scope.fatherName               = $scope.empInfo.fatherName;
            };

            $scope.load = function(id) {
                PfmsRegister.get({id : id}, function(result) {
                    $scope.pfmsRegister = result;
                });
            };

            if($stateParams.id){
                PfmsRegister.get({id : $stateParams.id}, function(result) {
                    $scope.pfmsRegister= result;
                    $scope.loadEmployeeInfo();
                });

            }

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
                $scope.$emit('stepApp:pfmsRegisterUpdate', result);
                $scope.isSaving = false;
                $state.go("pfmsRegister");
            };

            $scope.save = function () {
                $scope.pfmsRegister.updateBy = $scope.loggedInUser.id;
                $scope.pfmsRegister.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.pfmsRegister.id != null) {
                    PfmsRegister.update($scope.pfmsRegister, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsRegister.updated');
                } else {
                    $scope.pfmsRegister.createBy = $scope.loggedInUser.id;
                    $scope.pfmsRegister.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    PfmsRegister.save($scope.pfmsRegister, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsRegister.created');
                }
            };

        }]);
