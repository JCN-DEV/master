
'use strict';

angular.module('stepApp').controller('PfmsEmpMembershipFormDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'PfmsEmpMembershipForm','Relationship', 'User', 'Principal', 'DateUtils','HrEmployeeInfo',
        function($scope, $rootScope, $stateParams, $state, entity, PfmsEmpMembershipForm,Relationship, User, Principal, DateUtils, HrEmployeeInfo) {

            $scope.pfmsEmpMembershipForm = entity;
            $scope.hremployeeinfos = HrEmployeeInfo.query();
            $scope.relationships = Relationship.query();
            $scope.pfmsEmpMembershipFormOne = entity;

            $scope.load = function(id) {
                PfmsEmpMembershipForm.get({id : id}, function(result) {
                    $scope.pfmsEmpMembershipFormOne = result;
                });
            };

            $scope.isExitsData = true;
            $scope.duplicateCheckMember = function(){
                $scope.isExitsData = true;
                if($stateParams.id){
                    $scope.load($stateParams.id);
                    if($scope.pfmsEmpMembershipForm.employeeInfo.id != $scope.pfmsEmpMembershipFormOne.employeeInfo.id){
                        PfmsEmpMembershipForm.query(function(result){
                            angular.forEach(result,function(dtoInfo){
                                if(dtoInfo.employeeInfo.id == $scope.pfmsEmpMembershipForm.employeeInfo.id){
                                    $scope.isExitsData = false;
                                }
                            });
                        });
                    }

                }else{
                    PfmsEmpMembershipForm.query(function(result){
                        angular.forEach(result,function(dtoInfo){
                            if(dtoInfo.employeeInfo.id == $scope.pfmsEmpMembershipForm.employeeInfo.id){
                                $scope.isExitsData = false;
                            }
                        });
                    });
                }

            };

            var today=new Date();
            $scope.newDate = today;

            if($stateParams.id){
                PfmsEmpMembershipForm.get({id : $stateParams.id}, function(result) {
                    $scope.pfmsEmpMembershipForm= result;
                    $scope.loadEmployeeInfo();
                });

            }
            $scope.loadEmployeeInfo = function(){
                $scope.empInfo = $scope.pfmsEmpMembershipForm.employeeInfo;
                $scope.designationName          = $scope.empInfo.designationInfo.designationInfo.designationName;
                $scope.departmentName           = $scope.empInfo.departmentInfo.departmentInfo.departmentName;
                $scope.dutySide                 = $scope.empInfo.workArea.typeName;
                $scope.nationality              = $scope.empInfo.nationality;
                $scope.fatherName               = $scope.empInfo.fatherName;
                $scope.duplicateCheckMember();
            };

            $scope.isInRange = true;
            $scope.onChangePercentOfDeduct = function(){
                if($scope.pfmsEmpMembershipForm.percentOfDeduct < 5 || $scope.pfmsEmpMembershipForm.percentOfDeduct > 25){
                    $scope.isInRange = false;
                }else{
                    $scope.isInRange = true;
                }
            };

            $scope.isMinimumThreeYrsShow = false;
            $scope.onChangeEmployeeStatus = function(){
                if($scope.pfmsEmpMembershipForm.employeeStatusPfms == 'Temporary'){
                    $scope.isMinimumThreeYrsShow = true;
                }else{
                    $scope.isMinimumThreeYrsShow = false;
                }
            };

            $scope.curContributeChange = function(){
                $scope.pfmsEmpMembershipForm.curOwnContributeTot = $scope.pfmsEmpMembershipForm.curOwnContribute + $scope.pfmsEmpMembershipForm.curOwnContributeInt;
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
                $scope.$emit('stepApp:pfmsEmpMembershipFormUpdate', result);
                $scope.isSaving = false;
                $state.go("pfmsEmpMembershipForm");
            };

            $scope.save = function () {
                $scope.pfmsEmpMembershipForm.updateBy = $scope.loggedInUser.id;
                $scope.pfmsEmpMembershipForm.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.pfmsEmpMembershipForm.id != null) {
                    PfmsEmpMembershipForm.update($scope.pfmsEmpMembershipForm, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.pfmsEmpMembershipForm.updated');
                } else {
                    $scope.pfmsEmpMembershipForm.createBy = $scope.loggedInUser.id;
                    $scope.pfmsEmpMembershipForm.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    PfmsEmpMembershipForm.save($scope.pfmsEmpMembershipForm, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsEmpMembershipForm.created');

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




