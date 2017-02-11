'use strict';

angular.module('stepApp').controller('HrEmpAddressInfoDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', 'entity', 'HrEmpAddressInfo', 'HrEmployeeInfoByWorkArea','Principal','User','DateUtils','District','Upazila','MiscTypeSetupByCategory','Division',
        function($scope,$rootScope, $stateParams, $state, entity, HrEmpAddressInfo, HrEmployeeInfoByWorkArea, Principal, User, DateUtils, District, Upazila, MiscTypeSetupByCategory,Division) {

        $scope.hrEmpAddressInfo = entity;
        $scope.hremployeeinfos  = [];
        $scope.workAreaList     = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
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
        $scope.loadModelByWorkArea = function(workArea)
        {
            HrEmployeeInfoByWorkArea.get({areaid : workArea.id}, function(result) {
                $scope.hremployeeinfos = result;
                console.log("Total record: "+result.length);
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

        $scope.loadUpazilaByDistrict = function(districtObj){
            $scope.upazilaListFilter = [];
            angular.forEach($scope.upazilaList,function(upazilaObj)
            {
                if(districtObj.id == upazilaObj.district.id){
                    $scope.upazilaListFilter.push(upazilaObj);
                }
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmpAddressInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpAddressInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.isSaving = true;
                    $scope.hrEmpAddressInfo.updateBy = result.id;
                    $scope.hrEmpAddressInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.hrEmpAddressInfo.logId = 0;
                    $scope.hrEmpAddressInfo.logStatus = 6;
                    if ($scope.hrEmpAddressInfo.id != null)
                    {
                        HrEmpAddressInfo.update($scope.hrEmpAddressInfo, onSaveSuccess, onSaveError);
                        $rootScope.setWarningMessage('stepApp.hrEmpAddressInfo.updated');
                    }
                    else
                    {
                        $scope.hrEmpAddressInfo.createBy = result.id;
                        $scope.hrEmpAddressInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                        HrEmpAddressInfo.save($scope.hrEmpAddressInfo, onSaveSuccess, onSaveError);
                        $rootScope.setSuccessMessage('stepApp.hrEmpAddressInfo.created');
                    }
                });
            });
        };

        $scope.clear = function() {

        };
}]);
