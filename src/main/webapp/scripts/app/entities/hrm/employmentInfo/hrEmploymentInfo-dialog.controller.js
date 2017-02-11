'use strict';

angular.module('stepApp').controller('HrEmploymentInfoDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', 'entity', 'HrEmploymentInfo', 'HrEmployeeInfoByWorkArea', 'HrEmplTypeInfo', 'HrPayScaleSetup','User','Principal','DateUtils','MiscTypeSetupByCategory','PrlLocalitySetInfo',
        function($scope, $rootScope, $stateParams, $state, entity, HrEmploymentInfo, HrEmployeeInfoByWorkArea, HrEmplTypeInfo, HrPayScaleSetup, User, Principal, DateUtils, MiscTypeSetupByCategory,PrlLocalitySetInfo) {

        $scope.hrEmploymentInfo = entity;
        $scope.hrempltypeinfos  = HrEmplTypeInfo.query({id:'bystat'});
        $scope.hrpayscalesetups = HrPayScaleSetup.query({id:'bystat'});
        $scope.prllocalitysetinfos = PrlLocalitySetInfo.query();

        $scope.load = function(id) {
            HrEmploymentInfo.get({id : id}, function(result) {
                $scope.hrEmploymentInfo = result;
            });
        };

        $scope.hremployeeinfos  = [];
        $scope.workAreaList     = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});

        $scope.loadModelByWorkArea = function(workArea)
        {
            HrEmployeeInfoByWorkArea.get({areaid : workArea.id}, function(result) {
                $scope.hremployeeinfos = result;
                console.log("Total record: "+result.length);
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

        $scope.updatePrlDate = function()
        {
            var jnDt = $scope.hrEmploymentInfo.joiningDate;
            var prlDt = new Date(jnDt.getFullYear() + 61, jnDt.getMonth(), jnDt.getDate());

            $scope.hrEmploymentInfo.prlDate = prlDt;
            //console.log("JoinDt: "+jnDt+", PrlDt: "+prlDt);
            console.log("JoinDate: "+$scope.hrEmploymentInfo.joiningDate+", PrlDate: "+$scope.hrEmploymentInfo.prlDate);
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
            $state.go('hrEmploymentInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmploymentInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmploymentInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmploymentInfo.id != null)
            {
                $scope.hrEmploymentInfo.logId = 0;
                $scope.hrEmploymentInfo.logStatus = 6;
                HrEmploymentInfo.update($scope.hrEmploymentInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmploymentInfo.updated');
            }
            else
            {
                $scope.hrEmploymentInfo.logId = 0;
                $scope.hrEmploymentInfo.logStatus = 6;
                $scope.hrEmploymentInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmploymentInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmploymentInfo.save($scope.hrEmploymentInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmploymentInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
