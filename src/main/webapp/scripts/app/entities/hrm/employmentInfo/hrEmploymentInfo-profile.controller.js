'use strict';

angular.module('stepApp').controller('HrEmploymentInfoProfileController',
    ['$scope', '$stateParams', '$state', 'HrEmploymentInfo', 'HrEmployeeInfo', 'HrEmplTypeInfo', 'HrPayScaleSetup','User','Principal','DateUtils','PrlLocalitySetInfo',
        function($scope, $stateParams, $state, HrEmploymentInfo, HrEmployeeInfo, HrEmplTypeInfo, HrPayScaleSetup, User, Principal, DateUtils,PrlLocalitySetInfo) {

        $scope.hrEmploymentInfo = {};
        $scope.hrempltypeinfos = HrEmplTypeInfo.query({id:'bystat'});
        $scope.hrpayscalesetups = HrPayScaleSetup.query({id:'bystat'});
        $scope.prllocalitysetinfos = PrlLocalitySetInfo.query();
        $scope.load = function(id) {
            HrEmploymentInfo.get({id : id}, function(result) {
                $scope.hrEmploymentInfo = result;
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


        $scope.viewMode = true;
        $scope.addMode = false;
        $scope.noEmployeeFound = false;

        $scope.loadModel = function()
        {
            console.log("loadEmploymentProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmploymentInfo.query({id: 'my'}, function (result)
            {
                //console.log("result: "+JSON.stringify(result));
                if(result.length < 1)
                {
                    //console.log("result:, len: "+JSON.stringify(result));
                    $scope.addMode = true;
                    $scope.hrEmploymentInfo = $scope.initiateModel();
                    $scope.hrEmploymentInfo.viewMode = true;
                    $scope.hrEmploymentInfo.viewModeText = "Add";
                    $scope.loadEmployee();
                }
                else
                {
                    //console.log("result: data found: "+JSON.stringify(result));
                    $scope.hrEmploymentInfo = result[0];
                    $scope.hrEmployeeInfo = $scope.hrEmploymentInfo.employeeInfo;
                    $scope.hrEmploymentInfo.viewMode = true;
                    $scope.hrEmploymentInfo.viewModeText = "Edit";
                    $scope.hrEmploymentInfo.isLocked = false;
                    if($scope.hrEmploymentInfo.logStatus==0)
                    {
                        $scope.hrEmploymentInfo.isLocked = true;
                    }
                    $scope.updatePrlDate();
                }

            }, function (response)
            {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.addMode = true;
                $scope.loadEmployee();
            })
        };

        $scope.loadEmployee = function ()
        {
            console.log("loadEmployeeProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmployeeInfo.get({id: 'my'}, function (result)
            {
                $scope.hrEmployeeInfo = result;
                $scope.hrEmploymentInfo.viewMode = true;
                $scope.hrEmploymentInfo.viewModeText = "Add";
                $scope.updatePrlDate();

            }, function (response) {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.noEmployeeFound = true;
                $scope.isSaving = false;
                $scope.hrEmploymentInfo.viewMode = true;
                $scope.hrEmploymentInfo.viewModeText = "Add";
            })
        };
        $scope.loadModel();

        $scope.changeProfileMode = function (modelInfo)
        {
            if(modelInfo.viewMode)
            {
                modelInfo.viewMode = false;
                modelInfo.viewModeText = "Cancel";
            }
            else
            {
                modelInfo.viewMode = true;
                if(modelInfo.id==null)
                    modelInfo.viewModeText = "Add";
                else
                    modelInfo.viewModeText = "Edit";
            }
        };

        $scope.updatePrlDate = function()
        {
            console.log("birthdate: "+$scope.hrEmployeeInfo.birthDate+", nid:"+$scope.hrEmployeeInfo.quota);

            var prlYear = 61;
            if($scope.hrEmployeeInfo.quota=='Others' || $scope.hrEmployeeInfo.quota == 'Other' || $scope.hrEmployeeInfo.quota == 'others' || $scope.hrEmployeeInfo.quota == 'other')
            {
                prlYear = 59;
            }
            var brDt = new Date($scope.hrEmployeeInfo.birthDate);
            var oldPrl = new Date($scope.hrEmploymentInfo.prlDate);
            console.log("BirthDate: "+brDt+", PrdYear: "+prlYear);
            var prlDt = new Date(brDt.getFullYear() + prlYear, brDt.getMonth(), brDt.getDate());
            $scope.hrEmploymentInfo.prlDate = prlDt;

            $scope.hrEmploymentInfo.prlDateOld = oldPrl;

            if(oldPrl.getFullYear()==prlDt.getFullYear() &&  oldPrl.getMonth()==prlDt.getMonth() && oldPrl.getDate()==prlDt.getDate() )
            {
                $scope.prlDateChanged = false;
                console.log("Same Date...");
            }
            else{
                $scope.prlDateChanged = true;
                console.log("Not Same Date...");
            }

            console.log("OldPrl: "+$scope.hrEmploymentInfo.prlDate+", GenPrl: "+$scope.hrEmploymentInfo.prlDateOld);
        };

        $scope.initiateModel = function()
        {
            return {
                viewMode:true,
                viewModeText:'Add',
                presentInstitute: null,
                joiningDate: null,
                regularizationDate: null,
                jobConfNoticeNo: null,
                confirmationDate: null,
                officeOrderNo: null,
                officeOrderDate: null,
                logId: null,
                logStatus: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
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
            $scope.$emit('stepApp:hrEmploymentInfoUpdate', result);
            $scope.isSaving = false;
            $scope.hrEmploymentInfo.id=result.id;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.updateProfile = function (modelInfo)
        {
            $scope.isSaving = true;
            modelInfo.updateBy = $scope.loggedInUser.id;
            modelInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            modelInfo.activeStatus = true;

            //console.log("result: data found: "+JSON.stringify(modelInfo));
            if (modelInfo.id != null)
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 0;
                HrEmploymentInfo.update(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                modelInfo.isLocked = true;
            }
            else
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 0;
                modelInfo.employeeInfo = $scope.hrEmployeeInfo;
                modelInfo.createBy = $scope.loggedInUser.id;
                modelInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());

                HrEmploymentInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.clear = function() {

        };
}]);
