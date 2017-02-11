'use strict';

angular.module('stepApp').controller('HrEntertainmentBenefitProfileController',
    ['$scope', '$stateParams', '$state', 'HrEntertainmentBenefit', 'HrEmployeeInfo', 'MiscTypeSetupByCategory','User','Principal','DateUtils',
        function($scope, $stateParams, $state, HrEntertainmentBenefit, HrEmployeeInfo, MiscTypeSetupByCategory, User, Principal, DateUtils) {

        $scope.hrEntertainmentBenefit = {};
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.misctypesetups = MiscTypeSetupByCategory.get({cat:'JobCategory',stat:'true'});
        $scope.load = function(id) {
            HrEntertainmentBenefit.get({id : id}, function(result) {
                $scope.hrEntertainmentBenefit = result;
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
            console.log("loadEntertainmentBenefitProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEntertainmentBenefit.query({id: 'my'}, function (result)
            {
                console.log("result:, len: "+result.length);
                $scope.hrEntertainmentBenefitList = result;
                if($scope.hrEntertainmentBenefitList.length < 1)
                {
                    $scope.addMode = true;
                    $scope.hrEntertainmentBenefitList.push($scope.initiateModel());
                    $scope.loadEmployee();
                }
                else
                {
                    $scope.hrEmployeeInfo = $scope.hrEntertainmentBenefitList[0].employeeInfo;
                    angular.forEach($scope.hrEntertainmentBenefitList,function(modelInfo)
                    {
                        modelInfo.viewMode = true;
                        modelInfo.viewModeText = "Edit";
                        modelInfo.isLocked = false;
                        if(modelInfo.logStatus==0)
                        {
                            modelInfo.isLocked = true;
                        }
                    });
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
            HrEmployeeInfo.get({id: 'my'}, function (result) {
                $scope.hrEmployeeInfo = result;

            }, function (response) {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.noEmployeeFound = true;
                $scope.isSaving = false;
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
                {
                    if($scope.hrEntertainmentBenefitList.length > 1)
                    {
                        var indx = $scope.hrEntertainmentBenefitList.indexOf(modelInfo);
                        $scope.hrEntertainmentBenefitList.splice(indx, 1);
                    }
                    else
                    {
                        modelInfo.viewModeText = "Add";
                    }
                }
                else
                    modelInfo.viewModeText = "Edit";
            }
        };

        $scope.addMore = function()
        {
            $scope.hrEntertainmentBenefitList.push(
                {
                    viewMode:false,
                    viewModeText:'Cancel',
                    eligibilityDate: null,
                    amount: null,
                    totalDays: null,
                    notTakenReason: null,
                    logId:null,
                    logStatus:null,
                    logComments:null,
                    activeStatus: true,
                    createDate: null,
                    createBy: null,
                    updateDate: null,
                    updateBy: null,
                    id: null
                }
            );
        };

        $scope.initiateModel = function()
        {
            return {
                viewMode:true,
                viewModeText:'Add',
                eligibilityDate: null,
                amount: null,
                totalDays: null,
                notTakenReason: null,
                logId:null,
                logStatus:null,
                logComments:null,
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
            $scope.$emit('stepApp:hrEntertainmentBenefitUpdate', result);
            $scope.isSaving = false;
            $scope.hrEntertainmentBenefitList[$scope.selectedIndex].id=result.id;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.updateProfile = function (modelInfo, index)
        {
            $scope.selectedIndex = index;
            modelInfo.updateBy = $scope.loggedInUser.id;
            modelInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            modelInfo.activeStatus = true;

            if (modelInfo.id != null)
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 0;
                HrEntertainmentBenefit.update(modelInfo, onSaveSuccess, onSaveError);
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

                HrEntertainmentBenefit.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.clear = function() {

        };
}]);
