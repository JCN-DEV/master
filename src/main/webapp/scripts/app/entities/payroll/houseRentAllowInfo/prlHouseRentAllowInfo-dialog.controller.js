'use strict';

angular.module('stepApp').controller('PrlHouseRentAllowInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PrlHouseRentAllowInfo', 'PrlLocalitySetInfo', 'HrGazetteSetupByStatus','User','Principal','DateUtils','PrlHouseRentAllowInfoMinMaxByLocality',
        function($scope, $rootScope, $stateParams, $state, entity, PrlHouseRentAllowInfo, PrlLocalitySetInfo, HrGazetteSetupByStatus, User, Principal, DateUtils,PrlHouseRentAllowInfoMinMaxByLocality) {

        $scope.prlHouseRentAllowInfo = entity;
        $scope.prllocalitysetinfos = PrlLocalitySetInfo.query();
        //$scope.hrgazettesetups = HrGazetteSetup.query({size:500});
        $scope.hrgazettesetups = HrGazetteSetupByStatus.get({stat:'true'});
        $scope.load = function(id) {
            PrlHouseRentAllowInfo.get({id : id}, function(result) {
                $scope.prlHouseRentAllowInfo = result;
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

        $scope.basicMinValue = -1.0;
        $scope.basicMaxValue = -1.0;
        $scope.loadLocalictySetBasicMinMaxValue = function()
        {
            console.log("Loading BasicMinMax Value");
            if($scope.prlHouseRentAllowInfo.gazetteInfo == null)
                console.log("gezzeteObjectNullCheck");
            if($scope.prlHouseRentAllowInfo.localitySetInfo == null)
                console.log("LocalityObjectNullCheck");

            if($scope.prlHouseRentAllowInfo.gazetteInfo != null && $scope.prlHouseRentAllowInfo.localitySetInfo !=null )
            {
                console.log("GezzeteId: "+$scope.prlHouseRentAllowInfo.gazetteInfo.id+", LocalitySetId: "+$scope.prlHouseRentAllowInfo.localitySetInfo.id);
                PrlHouseRentAllowInfoMinMaxByLocality.get({gztid:$scope.prlHouseRentAllowInfo.gazetteInfo.id, lclstid: $scope.prlHouseRentAllowInfo.localitySetInfo.id}, function(result)
                {
                    console.log(JSON.stringify(result));
                    $scope.basicMinValue = (result.basicMin==null ? -1 : result.basicMin);
                    $scope.basicMaxValue = (result.basicMax==null ? -1 : result.basicMax);
                },function(response)
                {
                    console.log("data connection failed");
                });
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlHouseRentAllowInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('prlHouseRentAllowInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.prlHouseRentAllowInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlHouseRentAllowInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.prlHouseRentAllowInfo.id != null)
            {
                PrlHouseRentAllowInfo.update($scope.prlHouseRentAllowInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlHouseRentAllowInfo.updated');
            }
            else
            {
                $scope.prlHouseRentAllowInfo.createBy = $scope.loggedInUser.id;
                $scope.prlHouseRentAllowInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlHouseRentAllowInfo.save($scope.prlHouseRentAllowInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlHouseRentAllowInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
