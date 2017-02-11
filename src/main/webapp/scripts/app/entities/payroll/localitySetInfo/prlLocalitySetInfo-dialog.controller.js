'use strict';

angular.module('stepApp').controller('PrlLocalitySetInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PrlLocalitySetInfo', 'PrlLocalityInfo','User','Principal','DateUtils','PrlLocalitySetInfoUniqueness',
        function($scope, $rootScope, $stateParams, $state, entity, PrlLocalitySetInfo, PrlLocalityInfo, User, Principal, DateUtils,PrlLocalitySetInfoUniqueness) {

        $scope.prlLocalitySetInfo = entity;
        $scope.prllocalityinfos = PrlLocalityInfo.query();
        $scope.load = function(id) {
            PrlLocalitySetInfo.get({id : id}, function(result) {
                $scope.prlLocalitySetInfo = result;
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

        $scope.localitySetAlreadyExist = false;
        $scope.checkLocalitySetUniqByName = function()
        {
            console.log("name: "+$scope.prlLocalitySetInfo.name)
            if($scope.prlLocalitySetInfo.name != null && $scope.prlLocalitySetInfo.name.length > 0)
            {
                $scope.editForm.name.$pending = true;
                PrlLocalitySetInfoUniqueness.get({name: $scope.prlLocalitySetInfo.name}, function(result)
                {
                    //console.log(JSON.stringify(result));
                    $scope.isSaving = !result.isValid;
                    $scope.editForm.name.$pending = false;
                    if(result.isValid)
                    {
                        console.log("valid");
                        $scope.localitySetAlreadyExist = false;
                    }
                    else
                    {
                        console.log("not valid");
                        $scope.localitySetAlreadyExist = true;
                    }
                },function(response)
                {
                    console.log("data connection failed");
                    $scope.editForm.name.$pending = false;
                });
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlLocalitySetInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('prlLocalitySetInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            $scope.prlLocalitySetInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlLocalitySetInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.prlLocalitySetInfo.id != null) {
                PrlLocalitySetInfo.update($scope.prlLocalitySetInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlLocalitySetInfo.updated');
            }
            else
            {
                $scope.prlLocalitySetInfo.createBy = $scope.loggedInUser.id;
                $scope.prlLocalitySetInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlLocalitySetInfo.save($scope.prlLocalitySetInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlLocalitySetInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
