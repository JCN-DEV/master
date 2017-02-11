'use strict';

angular.module('stepApp').controller('InstGovBodyAccessDialogController',
    ['$scope', '$stateParams', '$q', 'entity', 'InstGovBodyAccess', 'Institute', 'User','Auth',
        function($scope, $stateParams, $q, entity, InstGovBodyAccess, Institute, User, Auth) {

        $scope.instGovBodyAccess = entity;
        $scope.institutes = Institute.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            InstGovBodyAccess.get({id : id}, function(result) {
                $scope.instGovBodyAccess = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instGovBodyAccessUpdate', result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instGovBodyAccess.id != null) {
                InstGovBodyAccess.update($scope.instGovBodyAccess, onSaveSuccess, onSaveError);
            } else {
                //$scope.instMemShip.user.login = $scope.user.login;
               // $scope.instGovBodyAccess.user.email = $scope.instGovBodyAccess.user.email;
                $scope.instGovBodyAccess.user.firstName = $scope.instGovBodyAccess.fullName;
                $scope.instGovBodyAccess.user.langKey = "en";
                $scope.instGovBodyAccess.user.activated = true;
                $scope.instGovBodyAccess.user.password = "123456";
                $scope.instGovBodyAccess.user.authorities = ["ROLE_INSTGOVBODY"];
                Auth.createAccount($scope.instGovBodyAccess.user).then(function (res) {
                    $scope.instGovBodyAccess.user = res;
                    InstGovBodyAccess.save($scope.instGovBodyAccess, onSaveSuccess, onSaveError);
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

        };
}]);
