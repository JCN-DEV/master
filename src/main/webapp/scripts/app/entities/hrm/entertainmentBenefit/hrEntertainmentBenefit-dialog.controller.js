'use strict';

angular.module('stepApp').controller('HrEntertainmentBenefitDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrEntertainmentBenefit', 'HrEmployeeInfoByWorkArea', 'MiscTypeSetupByCategory','User','Principal','DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, HrEntertainmentBenefit, HrEmployeeInfoByWorkArea, MiscTypeSetupByCategory, User, Principal, DateUtils) {

        $scope.hrEntertainmentBenefit = entity;
        $scope.misctypesetups = MiscTypeSetupByCategory.get({cat:'JobCategory'});
        $scope.load = function(id) {
            HrEntertainmentBenefit.get({id : id}, function(result) {
                $scope.hrEntertainmentBenefit = result;
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
            $state.go('hrEntertainmentBenefit');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEntertainmentBenefit.updateBy = $scope.loggedInUser.id;
            $scope.hrEntertainmentBenefit.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEntertainmentBenefit.id != null)
            {
                $scope.hrEntertainmentBenefit.logId = 0;
                $scope.hrEntertainmentBenefit.logStatus = 6;
                HrEntertainmentBenefit.update($scope.hrEntertainmentBenefit, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEntertainmentBenefit.updated');
            }
            else
            {
                $scope.hrEntertainmentBenefit.logId = 0;
                $scope.hrEntertainmentBenefit.logStatus = 6;
                $scope.hrEntertainmentBenefit.createBy = $scope.loggedInUser.id;
                $scope.hrEntertainmentBenefit.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEntertainmentBenefit.save($scope.hrEntertainmentBenefit, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEntertainmentBenefit.created');
            }
        };

        $scope.clear = function() {

        };
}]);
