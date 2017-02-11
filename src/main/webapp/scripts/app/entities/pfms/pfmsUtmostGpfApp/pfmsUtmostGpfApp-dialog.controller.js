'use strict';

angular.module('stepApp').controller('PfmsUtmostGpfAppDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PfmsUtmostGpfApp','PfmsEmpMembershipFormListByEmployee','PfmsEmpMembershipForm','PfmsRegister', 'User', 'Principal', 'DateUtils','HrEmployeeInfo',
        function($scope, $rootScope, $stateParams, $state, entity, PfmsUtmostGpfApp,PfmsEmpMembershipFormListByEmployee, PfmsEmpMembershipForm, PfmsRegister, User, Principal, DateUtils, HrEmployeeInfo) {

            $scope.pfmsUtmostGpfApp = entity;
            //$scope.hremployeeinfos = HrEmployeeInfo.query();
            $scope.hrEmployeeInfo = null;

            HrEmployeeInfo.get({id: 'my'}, function (result) {

                $scope.hrEmployeeInfo = result;
                $scope.designationName          = $scope.hrEmployeeInfo.designationInfo.designationInfo.designationName;
                $scope.departmentName           = $scope.hrEmployeeInfo.departmentInfo.departmentInfo.departmentName;
                $scope.dutySide                 = $scope.hrEmployeeInfo.workArea.typeName;
                $scope.nationality              = $scope.hrEmployeeInfo.nationality;
                $scope.fatherName               = $scope.hrEmployeeInfo.fatherName;
                $scope.pfmsUtmostGpfApp.prlDate               = $scope.hrEmployeeInfo.prlDate;
                $scope.loadPfAccountNo(result.id);
                $scope.loadPfmsRegister(result.id);
            });

            $scope.loadPfAccountNo= function(empId)
            {
                PfmsEmpMembershipFormListByEmployee.query({employeeInfoId:  empId},function(result){
                    angular.forEach(result,function(dtoInfo){
                        $scope.pfmsUtmostGpfApp.gpfNo = dtoInfo.accountNo;
                    });
                });

            };

            $scope.load = function(id) {
                PfmsUtmostGpfApp.get({id : id}, function(result) {
                    $scope.pfmsUtmostGpfApp = result;
                });
            };
            $scope.loadPfmsRegister = function(employeeId){
                PfmsRegister.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.employeeInfo.id == employeeId
                            && dtoInfo.applicationType == 'Provident Fund') {
                            if (dtoInfo.isBillRegister) {
                                $scope.pfmsUtmostGpfApp.billNo = dtoInfo.billNo;
                                $scope.pfmsUtmostGpfApp.billDate = dtoInfo.billDate;
                            }else{
                                $scope.pfmsUtmostGpfApp.tokenNo = dtoInfo.checkTokenNo;
                                $scope.pfmsUtmostGpfApp.tokenDate = dtoInfo.checkDate;
                            }
                        }
                    });
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
                $scope.$emit('stepApp:pfmsUtmostGpfAppUpdate', result);
                $scope.isSaving = false;
                $state.go("pfmsUtmostGpfApp");
            };

            $scope.save = function () {
                $scope.pfmsUtmostGpfApp.updateBy = $scope.loggedInUser.id;
                $scope.pfmsUtmostGpfApp.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.pfmsUtmostGpfApp.id != null) {
                    PfmsUtmostGpfApp.update($scope.pfmsUtmostGpfApp, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsUtmostGpfApp.updated');
                } else {
                    $scope.pfmsUtmostGpfApp.employeeInfo = $scope.hrEmployeeInfo;
                    $scope.pfmsUtmostGpfApp.createBy = $scope.loggedInUser.id;
                    $scope.pfmsUtmostGpfApp.approvalStatus = 'Pending';
                    $scope.pfmsUtmostGpfApp.applyDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.pfmsUtmostGpfApp.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    PfmsUtmostGpfApp.save($scope.pfmsUtmostGpfApp, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsUtmostGpfApp.created');
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


