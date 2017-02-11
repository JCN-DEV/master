'use strict';

angular.module('stepApp').controller('PrlEmpGenSalDetailInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PrlEmpGenSalDetailInfo', 'PrlEmpGeneratedSalInfo','User','Principal','DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, PrlEmpGenSalDetailInfo, PrlEmpGeneratedSalInfo, User, Principal, DateUtils) {

        $scope.prlEmpGenSalDetailInfo = entity;
        $scope.prlempgeneratedsalinfos = PrlEmpGeneratedSalInfo.query();
        $scope.load = function(id) {
            PrlEmpGenSalDetailInfo.get({id : id}, function(result) {
                $scope.prlEmpGenSalDetailInfo = result;
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

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlEmpGenSalDetailInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('prlEmpGenSalDetailInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            $scope.prlEmpGenSalDetailInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlEmpGenSalDetailInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.prlEmpGenSalDetailInfo.id != null) {
                PrlEmpGenSalDetailInfo.update($scope.prlEmpGenSalDetailInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlEmpGenSalDetailInfo.updated');
            } else {
                $scope.prlEmpGenSalDetailInfo.createBy = $scope.loggedInUser.id;
                $scope.prlEmpGenSalDetailInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlEmpGenSalDetailInfo.save($scope.prlEmpGenSalDetailInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlEmpGenSalDetailInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
