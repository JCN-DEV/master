'use strict';

angular.module('stepApp').controller('HrProjectInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrProjectInfo', 'MiscTypeSetupByCategory','User','Principal','DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, HrProjectInfo, MiscTypeSetupByCategory, User, Principal, DateUtils) {

        $scope.hrProjectInfo = entity;
        $scope.fundSourceList = MiscTypeSetupByCategory.get({cat:'SourceOfFund',stat:'true'});
        $scope.load = function(id) {
            HrProjectInfo.get({id : id}, function(result) {
                $scope.hrProjectInfo = result;
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
            $scope.$emit('stepApp:hrProjectInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrProjectInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrProjectInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrProjectInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.hrProjectInfo.id != null)
            {
                HrProjectInfo.update($scope.hrProjectInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrProjectInfo.updated');
            }
            else
            {
                $scope.hrProjectInfo.createBy = $scope.loggedInUser.id;
                $scope.hrProjectInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrProjectInfo.save($scope.hrProjectInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrProjectInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
