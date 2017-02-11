'use strict';

angular.module('stepApp').controller('HrEmployeeInfoDialogController',
    ['$rootScope','$scope', '$stateParams', '$state', '$q', 'entity', 'HrEmployeeInfo', 'HrDepartmentSetupByStatus', 'HrDesignationSetupByType', 'User','Principal','DateUtils','$translate','$timeout','Auth','MiscTypeSetupByCategory','HrGradeSetupByStatus','HrEmplTypeInfoByStatus','HrEmpWorkAreaDtlInfoByStatus','InstituteByCategory','InstCategory','HrEmployeeInfoDesigLimitCheck','Institute',
        function($rootScope, $scope, $stateParams, $state, $q, entity, HrEmployeeInfo, HrDepartmentSetupByStatus, HrDesignationSetupByType, User, Principal, DateUtils, $translate, $timeout, Auth, MiscTypeSetupByCategory,HrGradeSetupByStatus,HrEmplTypeInfoByStatus, HrEmpWorkAreaDtlInfoByStatus,InstituteByCategory,InstCategory,HrEmployeeInfoDesigLimitCheck,Institute)
        {
        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.registerAccount = {};
        $scope.regiUser = {};
        $scope.loggedInUser =   {};
        $scope.currentDate = new Date();

        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                    console.log("loggedInUser: "+JSON.stringify($scope.loggedInUser));
                });
            });
        };

        $scope.getLoggedInUser();

        $timeout(function (){angular.element('[ng-model="registerAccount.login"]').focus();});

        $scope.hrEmployeeInfo       = entity;
        $scope.hrdepartmentsetups   = HrDepartmentSetupByStatus.get({stat:'true'});
        $scope.hrdepartmentsetupsFltr   = $scope.hrdepartmentsetups;

        $scope.hrdesignationsetups  = HrDesignationSetupByType.get({type:'HRM'});
        $scope.hrDesigSetupListFltr = $scope.hrdesignationsetups;

        $scope.workAreaList         = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        $scope.workAreaDtlList      = HrEmpWorkAreaDtlInfoByStatus.get({stat:'true'});
        $scope.workAreaDtlListFiltr = $scope.workAreaDtlList;
        //$scope.employeeBasicTypes   = MiscTypeSetupByCategory.get({cat:'Employee',stat:'true'});
        $scope.encadrementList      = MiscTypeSetupByCategory.get({cat:'Encadrement',stat:'true'});
        $scope.employementTypeList  = HrEmplTypeInfoByStatus.get({stat:'true'});
        $scope.gradeInfoList        = HrGradeSetupByStatus.get({stat:'true'});
        $scope.instCategoryList     = InstCategory.query();
        $scope.instituteListAll     = Institute.query({size:500});
        $scope.instituteList        = $scope.instituteListAll;

        $scope.generateEmployeeId = function ()
        {
            var curDt = new Date();
            //console.log(curDt);
            var uniqCode = ""+curDt.getFullYear().toString()+""+(curDt.getMonth()+1).toString()+""+curDt.getDate().toString()+""+curDt.getHours().toString()+""+curDt.getMinutes()+""+curDt.getSeconds();
            //console.log(uniqCode);
            $scope.hrEmployeeInfo.employeeId = ""+uniqCode;
        };
        $scope.generateEmployeeId();

        $scope.users = User.query({filter: 'hremployeeinfo-is-null'});
        $q.all([$scope.hrEmployeeInfo.$promise, $scope.users.$promise]).then(function() {
            if (!$scope.hrEmployeeInfo.user || !$scope.hrEmployeeInfo.user.id) {
                return $q.reject();
            }
            return User.get({id : $scope.hrEmployeeInfo.user.id}).$promise;
        }).then(function(user) {
            $scope.users.push(user);
        });

        $scope.load = function(id) {
            HrEmployeeInfo.get({id : id}, function(result) {
                $scope.hrEmployeeInfo = result;
            });
        };

        $scope.loadWorkAreaDetailByWork = function(workArea)
        {
            $scope.workAreaDtlListFiltr = [];
            console.log("WorkAreaOrOrg:"+JSON.stringify(workArea)+"");
            angular.forEach($scope.workAreaDtlList,function(workAreaDtl)
            {
                if(workArea.id == workAreaDtl.workArea.id){
                    $scope.workAreaDtlListFiltr.push(workAreaDtl);
                }
            });
        };

        $scope.loadInstituteByCategory = function(categoryInfo)
        {
            //console.log(JSON.stringify(categoryInfo)+"");
            /*InstituteByCategory.get({cat : categoryInfo.id}, function(result) {
                $scope.instituteList = result;
            });*/
            //console.log("totalInst: "+$scope.instituteListAll.length);
            $scope.instituteList = [];
            angular.forEach($scope.instituteListAll,function(insitute)
            {
                //console.log("InstRow: "+JSON.stringify(insitute));
                if(insitute.instCategory != null)
                {
                    if(categoryInfo.id == insitute.instCategory.id){
                        $scope.instituteList.push(insitute);
                    }
                }
            });
        };

        $scope.loadDesigDeptByOrganization = function (orgnization)
        {
            $scope.hrDesigSetupListFltr = [];
            console.log("Total Desig: "+$scope.hrdesignationsetups.length);
            angular.forEach($scope.hrdesignationsetups,function(desigInfo)
            {
                if(desigInfo.organizationType=='Organization')
                {
                    if(desigInfo.organizationInfo != null)
                    {
                        //console.log("orgId: "+desigInfo.organizationInfo.id + ", Srcorgid; "+orgnization.id);
                        if(orgnization.id === desigInfo.organizationInfo.id)
                        {
                            $scope.hrDesigSetupListFltr.push(desigInfo);
                        }
                    }
                }
            });

            console.log("Total Section: "+$scope.hrdepartmentsetups.length);
            $scope.hrdepartmentsetupsFltr = [];
            angular.forEach($scope.hrdepartmentsetups,function(sectionInfo)
            {
                if(sectionInfo.organizationType=='Organization')
                {
                    if(sectionInfo.organizationInfo != null)
                    {
                        //console.log("sectionId: "+sectionInfo.organizationInfo.id+", SrcsecId"+sectionInfo);
                        if(orgnization.id === sectionInfo.organizationInfo.id)
                        {
                            $scope.hrdepartmentsetupsFltr.push(sectionInfo);
                        }
                    }
                }
            });
        };

        $scope.loadDesigDeptByInstitute = function (institute)
        {
            console.log("FilterByInst: "+JSON.stringify(institute));
            $scope.hrDesigSetupListFltr = [];
            console.log("totalDeisg: : "+$scope.hrdesignationsetups.length);
            angular.forEach($scope.hrdesignationsetups,function(desigInfo)
            {
                //console.log("desigId: "+JSON.stringify(desigInfo));
                //console.log("desigId: "+JSON.stringify(desigInfo.institute));
                if(desigInfo.organizationType=='Institute')
                {
                    if(institute.id == desigInfo.institute.id){
                        $scope.hrDesigSetupListFltr.push(desigInfo);
                    }
                }
            });

            $scope.hrdepartmentsetupsFltr = [];
            console.log("totalSection: "+$scope.hrdepartmentsetups.length);
            angular.forEach($scope.hrdepartmentsetups,function(sectionInfo)
            {
                if(sectionInfo.organizationType=='Institute')
                {
                    //console.log("sectionId1: "+JSON.stringify(sectionInfo));
                    //console.log("sectionId2: "+JSON.stringify(sectionInfo.institute));
                    if(institute.id == sectionInfo.institute.id){
                        $scope.hrdepartmentsetupsFltr.push(sectionInfo);
                    }
                }
            });
        };

        $scope.noOfTotalEmployeeInDesig = 0;
        $scope.noOfEmployeeInDesig = 0;
        $scope.employeeDesigLimitAllowed = true;
        $scope.checkDesignationLimit = function(desigInfo)
        {
            var refId = 0;
            if($scope.hrEmployeeInfo.organizationType=='Organization')
            {
                refId = $scope.hrEmployeeInfo.workAreaDtl.id;
            }
            else{
                refId = $scope.hrEmployeeInfo.institute.id;
            }
            if(desigInfo != null)
            {
                console.log("Type: "+$scope.hrEmployeeInfo.organizationType+", desigId: "+desigInfo.id+", refId: "+refId);
                HrEmployeeInfoDesigLimitCheck.get({orgtype:$scope.hrEmployeeInfo.organizationType, desigId: desigInfo.id, refid:refId}, function (result)
                {
                    console.log("DesigResult: "+JSON.stringify(result));
                    $scope.employeeDesigLimitAllowed = result.isValid;
                    $scope.noOfTotalEmployeeInDesig = result.totalEmployee;
                    $scope.noOfEmployeeInDesig = desigInfo.elocattedPosition;
                    $scope.isSaving = !result.isValid;
                    //console.log("Total: "+result.totalEmployee+", DesigVal: "+desigInfo.elocattedPosition);
                },function(response)
                {
                    console.log("data connection failed");
                    $scope.editForm.departmentInfo.$pending = false;
                });

                $scope.hrEmployeeInfo.gradeInfo = desigInfo.gradeInfo;
            }
        };

        $scope.calendar = {
            opened: {},
            dateFormat: 'dd-MM-yyyy',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmployeeInfoUpdate', result);
            $scope.isSaving = false;
            $('#employeeRegistrationConfirmModal').modal('show');
            //$state.go('hrEmployeeInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };
        $scope.save = function ()
        {
            console.log("Register User and Saving Employee ");
            //$('#employeeRegistrationConfirmModal').modal('show');
            $scope.registerEmployeeUser();

            // for testing purpose.
            //$scope.registerAccount.login = 'admin';
            //$scope.saveEmployeeInfo();
        };

        $scope.updatePrlDate = function()
        {
            if($scope.hrEmployeeInfo.birthDate && $scope.hrEmployeeInfo.quota)
            {
                var prlYear = 60;
                if($scope.hrEmployeeInfo.quota=='Others' || $scope.hrEmployeeInfo.quota == 'Other' || $scope.hrEmployeeInfo.quota == 'others' || $scope.hrEmployeeInfo.quota == 'other' || $scope.hrEmployeeInfo.quota == 'OTHERS')
                {
                    prlYear = 59;
                }
                var brDt = new Date($scope.hrEmployeeInfo.birthDate);
                brDt.setDate(brDt.getDate() - 1);

                var prlDt = new Date(brDt.getFullYear() + prlYear, brDt.getMonth(), brDt.getDate());
                var retirementDt = new Date(brDt.getFullYear() + prlYear + 1, brDt.getMonth(), brDt.getDate());
                $scope.hrEmployeeInfo.prlDate = prlDt;
                $scope.hrEmployeeInfo.retirementDate = retirementDt;
                $scope.hrEmployeeInfo.lastWorkingDay = retirementDt;
            }else {console.log("BirthDate and Quota is needed for PRL Calculation");}
        };

        $scope.saveEmployeeInfo = function()
        {
            console.log("login: "+$scope.registerAccount.login);
            User.get({login: $scope.registerAccount.login}, function (usrResult)
            {
                $scope.isSaving = true;
                $scope.hrEmployeeInfo.emailAddress  = $scope.registerAccount.email;
                $scope.regiUser = usrResult;
                $scope.regiUser.password = $scope.registerAccount.password;
                $scope.hrEmployeeInfo.updateBy = $scope.loggedInUser.id;
                $scope.hrEmployeeInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

                console.log("registerdUserInner: "+JSON.stringify($scope.regiUser)+"\n, email: "+$scope.hrEmployeeInfo.emailAddress);
                if ($scope.hrEmployeeInfo.id != null)
                {
                    $scope.hrEmployeeInfo.logId = 0;
                    $scope.hrEmployeeInfo.logStatus = 1;
                    HrEmployeeInfo.update($scope.hrEmployeeInfo, onSaveSuccess, onSaveError);
                    $rootScope.setWarningMessage('stepApp.hrEmployeeInfo.updated');
                }
                else
                {
                    $scope.hrEmployeeInfo.logId = 0;
                    $scope.hrEmployeeInfo.logStatus = 1;
                    $scope.hrEmployeeInfo.user = $scope.regiUser;
                    $scope.hrEmployeeInfo.user.password = $scope.registerAccount.password;
                    $scope.hrEmployeeInfo.createBy = $scope.loggedInUser.id;
                    $scope.hrEmployeeInfo.logComments = $scope.registerAccount.password;
                    $scope.hrEmployeeInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    HrEmployeeInfo.save($scope.hrEmployeeInfo, onSaveSuccess, onSaveError);
                    $rootScope.setSuccessMessage('stepApp.hrEmployeeInfo.created');
                }
            });
        };

        $scope.registerEmployeeUser = function ()
        {
            $scope.registerAccount.langKey = $translate.use();
            $scope.doNotMatch = null;
            $scope.error = null;
            $scope.errorUserExists = null;
            $scope.errorEmailExists = null;
            $scope.registerAccount.authorities = ["ROLE_HRM_USER"];
            $scope.registerAccount.activated = true;
            $scope.registerAccount.firstName = $scope.hrEmployeeInfo.fullName;
            $scope.hrEmployeeInfo.emailAddress  = $scope.registerAccount.email;
            console.log("FirstName; "+$scope.registerAccount.firstName+", login: "+$scope.registerAccount.login);

            Auth.createHrmAccount($scope.registerAccount).then(function ()
            {
                $scope.success = 'OK';
                console.log("User Login Success...");
                $scope.saveEmployeeInfo(); // call employee saving
            }).catch(function (response)
            {
                $scope.success = null;
                if (response.status === 400 && response.data === 'login already in use') {
                    $scope.errorUserExists = 'ERROR';
                } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                    $scope.errorEmailExists = 'ERROR';
                } else {
                    $scope.error = 'ERROR';
                }
            });

        };

        $scope.clear = function()
        {
            $('#employeeRegistrationConfirmModal').modal('hide');
            $timeout(function() {
                $state.go('hrEmployeeInfo');
            }, 1000);
        };

        $scope.setDefaultValue = function () {
            $scope.hrEmployeeInfo = {
                fullName: 'Yousuf Zaman',
                fullNameBn: 'Yousuf Bn',
                birthDate: '2016-01-01',
                apointmentGoDate: '2016-01-01',
                presentId: 'PresId',
                nationalId: '9999999999999999',
                emailAddress: 'redikod@yahoo.com',
                mobileNumber: '01972419696',
                logId:0,
                logStatus:1,
                logComments:'',
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
}]);
