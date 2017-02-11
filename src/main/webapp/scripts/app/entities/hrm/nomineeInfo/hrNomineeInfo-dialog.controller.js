'use strict';

angular.module('stepApp').controller('HrNomineeInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrNomineeInfo', 'HrEmployeeInfoByWorkArea','Principal','User','DateUtils','MiscTypeSetupByCategory',
        function($scope, $rootScope, $stateParams, $state, entity, HrNomineeInfo, HrEmployeeInfoByWorkArea, Principal, User, DateUtils, MiscTypeSetupByCategory) {

        $scope.hrNomineeInfo = entity;
        $scope.nomineeRelationships = MiscTypeSetupByCategory.get({cat:'NomineeRelationship'});
        $scope.load = function(id) {
            HrNomineeInfo.get({id : id}, function(result) {
                $scope.hrNomineeInfo = result;
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
            $scope.$emit('stepApp:hrNomineeInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrNomineeInfo');
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
                    $scope.hrNomineeInfo.updateBy = result.id;
                    $scope.hrNomineeInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

                    if ($scope.hrNomineeInfo.id != null)
                    {
                        $scope.hrNomineeInfo.logId = 0;
                        $scope.hrNomineeInfo.logStatus = 6;
                        HrNomineeInfo.update($scope.hrNomineeInfo, onSaveSuccess, onSaveError);
                        $rootScope.setWarningMessage('stepApp.hrNomineeInfo.updated');
                    }
                    else
                    {
                        $scope.hrNomineeInfo.logId = 0;
                        $scope.hrNomineeInfo.logStatus = 6;
                        $scope.hrNomineeInfo.createBy = result.id;
                        $scope.hrNomineeInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                        HrNomineeInfo.save($scope.hrNomineeInfo, onSaveSuccess, onSaveError);
                        $rootScope.setSuccessMessage('stepApp.hrNomineeInfo.created');
                    }
                });
            });
        };

        $scope.clear = function() {

        };
}]);
