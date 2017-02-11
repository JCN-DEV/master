'use strict';

angular.module('stepApp').controller('HrDesignationHeadSetupDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrDesignationHeadSetup','Principal','User','DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, HrDesignationHeadSetup, Principal, User, DateUtils) {

        $scope.hrDesignationHeadSetup = entity;
        $scope.load = function(id) {
            HrDesignationHeadSetup.get({id : id}, function(result) {
                $scope.hrDesignationHeadSetup = result;
            });
        };

        $scope.levelList = $rootScope.generateYearList(0, 15);

        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                    console.log("loggedInUser: "+JSON.stringify($scope.loggedInUser));
                });
            });
        };
        $scope.getLoggedInUser();

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrDesignationHeadSetupUpdate', result);
            $scope.isSaving = false;
            $state.go('hrDesignationHeadSetup');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrDesignationHeadSetup.updateBy = $scope.loggedInUser.id;
            $scope.hrDesignationHeadSetup.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.hrDesignationHeadSetup.id != null)
            {
                HrDesignationHeadSetup.update($scope.hrDesignationHeadSetup, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrDesignationHeadSetup.updated');
            } else
            {
                $scope.hrDesignationHeadSetup.createBy = $scope.loggedInUser.id;
                $scope.hrDesignationHeadSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrDesignationHeadSetup.save($scope.hrDesignationHeadSetup, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrDesignationHeadSetup.created');
            }
        };

        $scope.clear = function() {
        };
}]);
