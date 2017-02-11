'use strict';

angular.module('stepApp').controller('HrEmpWorkAreaDtlInfoDialogController',
    ['$scope','$rootScope', '$stateParams', 'entity','$state', 'HrEmpWorkAreaDtlInfo', 'MiscTypeSetupByCategory', 'Division', 'District', 'Upazila','User','Principal','DateUtils',
        function($scope,$rootScope, $stateParams, entity, $state, HrEmpWorkAreaDtlInfo, MiscTypeSetupByCategory, Division, District, Upazila, User, Principal, DateUtils) {

        $scope.hrEmpWorkAreaDtlInfo = entity;
        $scope.workAreaList     = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        $scope.divisions        = Division.query();
        $scope.districtList     = District.query({size:500});
        $scope.districtListFilter = $scope.districtList;
        $scope.upazilaList      = Upazila.query({size:500});
        $scope.upazilaListFilter = $scope.upazilaList;

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

        $scope.getLoggedInUser();

        $scope.load = function(id) {
            HrEmpWorkAreaDtlInfo.get({id : id}, function(result) {
                $scope.hrEmpWorkAreaDtlInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmpWorkAreaDtlInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpWorkAreaDtlInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            $scope.hrEmpWorkAreaDtlInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpWorkAreaDtlInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.hrEmpWorkAreaDtlInfo.id != null) {
                HrEmpWorkAreaDtlInfo.update($scope.hrEmpWorkAreaDtlInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpWorkAreaDtlInfo.updated');
            } else {
                $scope.hrEmpWorkAreaDtlInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpWorkAreaDtlInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpWorkAreaDtlInfo.save($scope.hrEmpWorkAreaDtlInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpWorkAreaDtlInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
