'use strict';

angular.module('stepApp').controller('HrGradeSetupDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrGradeSetup', 'HrClassInfo','Principal','User','DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, HrGradeSetup, HrClassInfo, Principal, User, DateUtils) {

        $scope.hrGradeSetup = entity;
        $scope.hrclassinfos = HrClassInfo.query({id:'bystat'});
        $scope.load = function(id)
        {
            HrGradeSetup.get({id : id}, function(result) {
                $scope.hrGradeSetup = result;
            });
        };

        var onSaveSuccess = function (result)
        {
            $scope.$emit('stepApp:hrGradeSetupUpdate', result);
            $scope.isSaving = false;
            $state.go('hrGradeSetup');
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
                    $scope.hrGradeSetup.updateBy = result.id;
                    $scope.hrGradeSetup.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    if ($scope.hrGradeSetup.id != null)
                    {
                        HrGradeSetup.update($scope.hrGradeSetup, onSaveSuccess, onSaveError);
                        $rootScope.setWarningMessage('stepApp.hrGradeSetup.updated');
                    }
                    else
                    {
                        $scope.hrGradeSetup.createBy = result.id;
                        $scope.hrGradeSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                        HrGradeSetup.save($scope.hrGradeSetup, onSaveSuccess, onSaveError);
                        $rootScope.setSuccessMessage('stepApp.hrGradeSetup.created');
                    }
                });
            });
        };

        $scope.clear = function() {

        };
}]);
