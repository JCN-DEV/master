'use strict';

angular.module('stepApp').controller('PfmsDeductionFinalizeDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PfmsDeductionFinalize','PfmsEmpMembershipForm', 'User', 'Principal', 'DateUtils','HrEmployeeInfo',
        function($scope, $rootScope, $stateParams, $state, entity, PfmsDeductionFinalize, PfmsEmpMembershipForm,User, Principal, DateUtils, HrEmployeeInfo) {

            $scope.pfmsDeductionFinalize = entity;
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
                PfmsDeductionFinalize.get({id : $stateParams.id}, function(result) {
                    $scope.pfmsDeductionFinalize = result;
                    $scope.loadEmployeeInfo();
                });

            }

            $scope.loadEmployeeInfo = function(){
                $scope.empInfo = $scope.pfmsDeductionFinalize.employeeInfo;
                $scope.designationName          = $scope.empInfo.designationInfo.designationInfo.designationName;
                $scope.departmentName           = $scope.empInfo.departmentInfo.departmentInfo.departmentName;
                $scope.dutySide                 = $scope.empInfo.workArea.typeName;
                $scope.nationality              = $scope.empInfo.nationality;
                $scope.fatherName               = $scope.empInfo.fatherName;
                $scope.retirementDate               = $scope.empInfo.retirementDate;
                $scope.loadPfAccountNo();
            };

            $scope.loadPfAccountNo= function()
            {
                PfmsEmpMembershipForm.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.employeeInfo.id == $scope.pfmsDeductionFinalize.employeeInfo.id && dtoInfo.activeStatus == true){
                            $scope.accountNo = dtoInfo.accountNo;
                        }
                    });
                });

            };

            $scope.load = function(id) {
                PfmsDeductionFinalize.get({id : id}, function(result) {
                    $scope.pfmsDeductionFinalize = result;
                });
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
                $scope.$emit('stepApp:pfmsDeductionFinalizeUpdate', result);
                $scope.isSaving = false;
                $state.go("pfmsDeductionFinalize");
            };

            $scope.save = function () {
                $scope.pfmsDeductionFinalize.updateBy = $scope.loggedInUser.id;
                $scope.pfmsDeductionFinalize.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.pfmsDeductionFinalize.id != null) {
                    PfmsDeductionFinalize.update($scope.pfmsDeductionFinalize, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsDeductionFinalize.updated');
                } else {
                    $scope.pfmsDeductionFinalize.createBy = $scope.loggedInUser.id;
                    $scope.pfmsDeductionFinalize.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    PfmsDeductionFinalize.save($scope.pfmsDeductionFinalize, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsDeductionFinalize.created');
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

        }]);



