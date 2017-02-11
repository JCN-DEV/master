'use strict';

angular.module('stepApp').controller('InstMemShipDialogController',
    ['$scope', '$rootScope', '$state', 'MemShipByEmail', '$stateParams', 'entity', 'InstMemShip','Auth','User',
        function($scope, $rootScope, $state, MemShipByEmail, $stateParams, entity, InstMemShip,Auth,User) {

        $scope.instMemShip = entity;
        $scope.load = function(id) {
            InstMemShip.get({id : id}, function(result) {
                $scope.instMemShip = result;
            });
        };

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
               /* result.user.activated = true;
                User.update();*/

            $scope.$emit('stepApp:instMemShipUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('instituteInfo.instMemShip',{},{reload:true});

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

            MemShipByEmail.get({email: $scope.instMemShip.email}, function (instMemShip) {

                $scope.message = "The  email address is already existed.";
            });
        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instMemShip.id != null) {
                InstMemShip.update($scope.instMemShip, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.instMemShip.updated');
            } else {
                //$scope.instMemShip.user.login = $scope.user.login;
                $scope.instMemShip.user.email = $scope.instMemShip.email;
                $scope.instMemShip.user.firstName = $scope.instMemShip.fullName;
                $scope.instMemShip.user.langKey = "en";
                //$scope.instMemShip.user.activated = true;
                $scope.instMemShip.user.password = "123456";
                $scope.instMemShip.user.authorities = ["ROLE_MANEGINGCOMMITTEE"];

                Auth.createAccount($scope.instMemShip.user).then(function (res) {
                    $scope.instMemShip.user = res;
                    /*MpoCommitteePersonInfo.save($scope.mpoCommitteePersonInfo, function (result){
                        $state.go('allCommitteeMembers', null, { reload: true });
                    });*/
                    InstMemShip.save($scope.instMemShip, onSaveSuccess, onSaveError);
                    $rootScope.setSuccessMessage('stepApp.instMemShip.created');
                }).catch(function (response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });

            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };
}]);
