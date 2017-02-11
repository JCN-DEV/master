'use strict';

angular.module('stepApp').controller('HrWingHeadSetupDialogController',
    ['$scope', '$stateParams','$rootScope', '$state', 'entity', 'HrWingHeadSetup', 'HrWingSetup','Principal','User','DateUtils','HrEmployeeInfosByDesigLevel','$window',
        function($scope, $stateParams, $rootScope, $state, entity, HrWingHeadSetup, HrWingSetup,Principal,User,DateUtils,HrEmployeeInfosByDesigLevel,$window) {

        $scope.hrWingHeadSetup = entity;

        $scope.load = function(id)
        {
            HrWingHeadSetup.get({id : id}, function(result) {
                $scope.hrWingHeadSetup = result;
            });
        };

        HrEmployeeInfosByDesigLevel.get({desigLevel : 3}, function(result) {
            $scope.hremployeeinfos = result;
        });

        $scope.loadWingEntity = function ()
        {
            console.log("EntityId: "+entity.id);
            console.log("WingIdStateParam: "+$stateParams.wingid);
            if($stateParams.wingid)
            {
                HrWingSetup.get({id : $stateParams.wingid}, function(result) {
                    $scope.hrWingHeadSetup.wingInfo = result;
                    console.log("HeadWingLoaded: "+$stateParams.wingid);
                });
            }
        };

        $scope.loggedInUser =   {};
        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                    $scope.loadWingEntity();
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

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:hrWingHeadSetupUpdate', result);
            $scope.isSaving = false;
            //$state.go('hrWingHeadSetup');
            $window.history.back();
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrWingHeadSetup.updateBy = $scope.loggedInUser.id;
            $scope.hrWingHeadSetup.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.hrWingHeadSetup.id != null)
            {
                HrWingHeadSetup.update($scope.hrWingHeadSetup, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.hrWingHeadSetup.updated');
            }
            else
            {
                $scope.hrWingHeadSetup.createBy = $scope.loggedInUser.id;
                $scope.hrWingHeadSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrWingHeadSetup.save($scope.hrWingHeadSetup, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.hrWingHeadSetup.created');
            }
        };

        $scope.clear = function() {

        };
}]);
