'use strict';

angular.module('stepApp').controller('PrlLocalityInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state','entity', 'PrlLocalityInfo', 'District','User','Principal','DateUtils','PrlLocalityInfoUniqueness',
        function($scope, $rootScope, $stateParams, $state, entity, PrlLocalityInfo, District, User, Principal, DateUtils,PrlLocalityInfoUniqueness) {

        $scope.prlLocalityInfo = entity;
        $scope.districts = District.query();
        $scope.load = function(id) {
            PrlLocalityInfo.get({id : id}, function(result) {
                $scope.prlLocalityInfo = result;
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
            $scope.$emit('stepApp:prlLocalityInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('prlLocalityInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.localityAlreadyExist = false;
        $scope.checkLocalityUniqByNameAndDistrict = function()
        {
            console.log("DistId: "+$scope.prlLocalityInfo.district.id+", name: "+$scope.prlLocalityInfo.name)
            if($scope.prlLocalityInfo.district.id !=null && $scope.prlLocalityInfo.name != null && $scope.prlLocalityInfo.name.length > 0)
            {
                $scope.editForm.name.$pending = true;
                PrlLocalityInfoUniqueness.get({distid:$scope.prlLocalityInfo.district.id, name: $scope.prlLocalityInfo.name}, function(result)
                {
                    //console.log(JSON.stringify(result));
                    $scope.isSaving = !result.isValid;
                    $scope.editForm.name.$pending = false;
                    if(result.isValid)
                    {
                        console.log("valid");
                        $scope.localityAlreadyExist = false;
                    }
                    else
                    {
                        console.log("not valid");
                        $scope.localityAlreadyExist = true;
                    }
                },function(response)
                {
                    console.log("data connection failed");
                    $scope.editForm.name.$pending = false;
                });
            }
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.prlLocalityInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlLocalityInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            //console.log(JSON.stringify($scope.prlLocalityInfo));
            if ($scope.prlLocalityInfo.id != null)
            {
                PrlLocalityInfo.update($scope.prlLocalityInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlLocalityInfo.updated');
            }
            else
            {
                $scope.prlLocalityInfo.createBy = $scope.loggedInUser.id;
                $scope.prlLocalityInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlLocalityInfo.save($scope.prlLocalityInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlLocalityInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
