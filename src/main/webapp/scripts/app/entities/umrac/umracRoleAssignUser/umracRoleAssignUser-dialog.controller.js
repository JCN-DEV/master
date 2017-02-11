'use strict';

angular.module('stepApp').controller('UmracRoleAssignUserDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'UmracRoleAssignUser', 'UmracIdentitySetup', 'UmracRoleSetup', 'UserExistsChecker',
        function ($scope, $stateParams, $state, entity, UmracRoleAssignUser, UmracIdentitySetup, UmracRoleSetup, UserExistsChecker) {

            $scope.umracRoleAssignUser = entity;
            $scope.umracidentitysetups = UmracIdentitySetup.query();
            $scope.umracrolesetups = UmracRoleSetup.query();
            $scope.load = function (id) {
                UmracRoleAssignUser.get({id: id}, function (result) {
                    $scope.umracRoleAssignUser = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:umracRoleAssignUserUpdate', result);
                $state.go('umracRoleAssignUser');
            };

            $scope.save = function () {
                /*$scope.userCheker = '';
                UserExistsChecker.get({id: id}, function (result) {
                    $scope.userCheker = result;
                });*/

                if ($scope.umracRoleAssignUser.id != null) {
                    UmracRoleAssignUser.update($scope.umracRoleAssignUser, onSaveFinished);
                } else {
                    UmracRoleAssignUser.save($scope.umracRoleAssignUser, onSaveFinished);
                }


            };

            $scope.clear = function () {
                $state.go('umracRoleAssignUser');
            };
            $scope.filterUserByStatus = function () {
                                                return function (item) {
                                                    if (item.status == true)
                                                    {
                                                        return true;
                                                    }
                                                    return false;
                                                };
                                            };
            $scope.filterRoleByStatus = function () {
                                                            return function (item) {
                                                                if (item.status == true)
                                                                {
                                                                    return true;
                                                                }
                                                                return false;
                                                            };
                                                        };
        }]);
