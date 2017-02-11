'use strict';

angular.module('stepApp').controller('PrlOnetimeAllowInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PrlOnetimeAllowInfo','User','Principal','DateUtils','PrlAllowDeductInfoByType',
        function($scope, $rootScope, $stateParams, $state, entity, PrlOnetimeAllowInfo, User, Principal, DateUtils,PrlAllowDeductInfoByType) {

        $scope.prlOnetimeAllowInfo = entity;
        $scope.prlAllowanceList = PrlAllowDeductInfoByType.query({type:'OnetimeAllowance'});
        $scope.load = function(id) {
            PrlOnetimeAllowInfo.get({id : id}, function(result) {
                $scope.prlOnetimeAllowInfo = result;
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
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlOnetimeAllowInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('prlOnetimeAllowInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            $scope.prlOnetimeAllowInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlOnetimeAllowInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.prlOnetimeAllowInfo.id != null)
            {
                PrlOnetimeAllowInfo.update($scope.prlOnetimeAllowInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlOnetimeAllowInfo.updated');
            } else
            {
                $scope.prlOnetimeAllowInfo.createBy = $scope.loggedInUser.id;
                $scope.prlOnetimeAllowInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlOnetimeAllowInfo.save($scope.prlOnetimeAllowInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlOnetimeAllowInfo.created');
            }
        };

        $scope.clear = function() {
        };
}]);
