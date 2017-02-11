'use strict';

angular.module('stepApp').controller('SmsServiceDepartmentDialogController',
    ['$scope', '$state','$stateParams', 'entity', 'SmsServiceDepartment', 'User', 'SmsServiceTypeByStatus', 'SmsServiceName', 'Department','Principal',
        function($scope, $state, $stateParams, entity, SmsServiceDepartment, User, SmsServiceTypeByStatus, SmsServiceName, Department, Principal) {

        $scope.smsServiceDepartment = entity;
        //$scope.users = User.query();
        $scope.smsservicetypes = SmsServiceTypeByStatus.query({stat:true});
        $scope.smsservicenames = SmsServiceName.query();
        $scope.departments = Department.query();
        $scope.filteredNameList = $scope.smsservicenames;
        $scope.SelectedName = "Select a Name";

        $scope.load = function(id)
        {
            SmsServiceDepartment.get({id : id}, function(result) {
                $scope.smsServiceDepartment = result;
            });
        };

        $scope.loadServiceNameByTypes = function(data){
            $scope.filteredNameList = [];
            angular.forEach($scope.smsservicenames,function(nameData){
                if(data.id == nameData.smsServiceType.id){
                    $scope.filteredNameList.push(nameData);
                }
            });
        };

        var onSaveSuccess = function (result)
        {
            $scope.$emit('stepApp:smsServiceDepartmentUpdate', result);
            $scope.isSaving = false;
            $state.go('smsServiceDepartment');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            if ($scope.smsServiceDepartment.id != null)
            {
                Principal.identity().then(function (account)
                {
                    User.get({login: account.login}, function (result) {
                        $scope.smsServiceDepartment.user = result;
                        SmsServiceDepartment.update($scope.smsServiceDepartment, onSaveSuccess, onSaveError);
                    });
                });

            } else
            {
                Principal.identity().then(function (account)
                {
                    User.get({login: account.login}, function (result) {
                        $scope.smsServiceDepartment.user = result;
                        SmsServiceDepartment.save($scope.smsServiceDepartment, onSaveSuccess, onSaveError);
                    });
                });
            }
        };

        $scope.clear = function() {

        };
}]);
