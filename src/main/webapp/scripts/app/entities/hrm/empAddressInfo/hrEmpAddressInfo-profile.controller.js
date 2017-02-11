'use strict';

angular.module('stepApp').controller('HrEmpAddressInfoProfileController',
    ['$scope', '$stateParams', '$state', '$filter', 'HrEmpAddressInfo', 'HrEmployeeInfo','Principal','User','DateUtils','District','Upazila','Division',
        function($scope, $stateParams, $state, $filter, HrEmpAddressInfo, HrEmployeeInfo, Principal, User, DateUtils,District,Upazila,Division) {

        $scope.hrEmpAddressInfo = {};
        //$scope.hremployeeinfos = HrEmployeeInfo.query();

        $scope.divisions        = Division.query();

        $scope.districtList = District.query({size:500});
        $scope.districtListFilter = $scope.districtList;

        $scope.upazilaList  = Upazila.query({size:500});
        $scope.upazilaListFilter = $scope.upazilaList;
        $scope.load = function(id) {
            HrEmpAddressInfo.get({id : id}, function(result) {
                $scope.hrEmpAddressInfo = result;
            });
        };

        $scope.loadDistrictByDivision = function(divisionObj)
        {
            $scope.districtListFilter = [];
            angular.forEach($scope.districtList,function(districtObj)
            {
                if(divisionObj.id == districtObj.division.id){
                    $scope.districtListFilter.push(districtObj);
                }
            });
        };

        $scope.loadUpazilaByDistrict = function(districtObj)
        {
            $scope.upazilaListFilter = [];
            angular.forEach($scope.upazilaList,function(upazilaObj)
            {
                if(districtObj.id == upazilaObj.district.id){
                    $scope.upazilaListFilter.push(upazilaObj);
                }
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

        var ADDR_TYPE_PERMANENT = 'Permanent';
        var ADDR_TYPE_PRESENT   = 'Present';
        var ADDR_TYPE_EMERGENCY = 'Emergency';

        $scope.loadModel = function()
        {
            console.log("loadChildProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmpAddressInfo.query({id: 'my'}, function (result)
            {
                console.log("result:, len: "+result.length);
                $scope.hrEmpAddressInfoList = result;
                if($scope.hrEmpAddressInfoList.length < 1)
                {
                    $scope.addMode = true;
                    $scope.hrEmpAddressInfoList.push($scope.initiateAddress(ADDR_TYPE_PERMANENT));
                    $scope.hrEmpAddressInfoList.push($scope.initiateAddress(ADDR_TYPE_PRESENT));
                    $scope.hrEmpAddressInfoList.push($scope.initiateAddress(ADDR_TYPE_EMERGENCY));

                    //$scope.hrEmpAddressInfoList = $scope.shortList($scope.hrEmpAddressInfoList);
                    $scope.loadEmployee();
                }
                else
                {
                    $scope.hrEmployeeInfo = $scope.hrEmpAddressInfoList[0].employeeInfo;
                    angular.forEach($scope.hrEmpAddressInfoList,function(modelInfo)
                    {
                        modelInfo.viewMode = true;
                        modelInfo.viewModeText = "Edit";
                        modelInfo.isLocked = false;
                        if(modelInfo.logStatus==0)
                        {
                            modelInfo.isLocked = true;
                        }
                    });

                    var permAddObj = $filter('filter')($scope.hrEmpAddressInfoList, {addressType: ADDR_TYPE_PERMANENT })[0];
                    if(!permAddObj){
                        $scope.hrEmpAddressInfoList.push($scope.initiateAddress(ADDR_TYPE_PERMANENT));
                    }

                    var presAddObj = $filter('filter')($scope.hrEmpAddressInfoList, {addressType: ADDR_TYPE_PRESENT })[0];
                    if(!presAddObj){
                        $scope.hrEmpAddressInfoList.push($scope.initiateAddress(ADDR_TYPE_PRESENT));
                    }

                    var emerAddObj = $filter('filter')($scope.hrEmpAddressInfoList, {addressType: ADDR_TYPE_EMERGENCY })[0];
                    if(!emerAddObj){
                        $scope.hrEmpAddressInfoList.push($scope.initiateAddress(ADDR_TYPE_EMERGENCY));
                    }

                    $scope.hrEmpAddressInfoList = $scope.shortList($scope.hrEmpAddressInfoList);
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
                    modelInfo.viewModeText = "Add";
                else
                    modelInfo.viewModeText = "Edit";
            }
        };

        $scope.initiateAddress = function(addressType)
        {
            return {
                viewMode:true,
                viewModeText:'Add',
                addressType: addressType,
                houseNumber: null,
                houseNumberBn: null,
                roadNumber: null,
                roadNumberBn: null,
                villageName: null,
                villageNameBn: null,
                postOffice: null,
                postOfficeBn: null,
                contactNumber: null,
                contactName: null,
                contactNameBn: null,
                address: null,
                activeStatus: true,
                logId:0,
                logStatus:1,
                logComments:null,
                id: null
            };
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmpAddressInfoUpdate', result);
            $scope.isSaving = false;
            $scope.hrEmpAddressInfoList[$scope.selectedIndex].id=result.id;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            $scope.viewMode = true;
        };

        $scope.shortList = function(modelList)
        {
            var newModelList = [];
            angular.forEach(modelList,function(modelInfo)
            {
                if(modelInfo.addressType == ADDR_TYPE_PERMANENT)
                {
                    newModelList[0] = modelInfo;
                }
                else if(modelInfo.addressType == ADDR_TYPE_PRESENT)
                {
                    newModelList[1] = modelInfo;
                }
                else if(modelInfo.addressType == ADDR_TYPE_EMERGENCY)
                {
                    newModelList[2] = modelInfo;
                }
            });
            return newModelList;
        };

        $scope.shortListDefault = function(dataList)
        {
            dataList.sort(function(a, b)
            {
                var nameA=a.addressType.toLowerCase(), nameB=b.addressType.toLowerCase()
                if (nameA < nameB) //sort string ascending
                    return -1
                if (nameA > nameB)
                    return 1
                return 0 //default return value (no sorting)
            });
            return dataList;
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
                HrEmpAddressInfo.update(modelInfo, onSaveSuccess, onSaveError);
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

                HrEmpAddressInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.clear = function() {

        };

       $scope.copyFromUppper = function(indx)
       {
           console.log("Current Index: "+indx);
           var topObj = $scope.hrEmpAddressInfoList[indx-1];
           var curObj = $scope.hrEmpAddressInfoList[indx];
           //console.log(JSON.stringify(topObj));
           //console.log(JSON.stringify(curObj));
           if(topObj.id)
           {
               console.log("Yes, top object is valid");
               curObj.houseNumber   = topObj.houseNumber;
               curObj.houseNumberBn = topObj.houseNumberBn;
               curObj.roadNumber    = topObj.roadNumber;
               curObj.roadNumberBn  = topObj.roadNumberBn;
               curObj.villageName   = topObj.villageName;
               curObj.villageNameBn = topObj.villageNameBn;
               curObj.postOffice    = topObj.postOffice;
               curObj.postOfficeBn  = topObj.postOfficeBn;
               curObj.contactNumber = topObj.contactNumber;
               curObj.division      = topObj.division;
               curObj.district      = topObj.district;
               curObj.upazila       = topObj.upazila;

           }
       };
}]);
