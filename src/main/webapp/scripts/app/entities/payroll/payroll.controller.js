'use strict';

angular.module('stepApp')
    .controller('payrollHomeController',
    ['$rootScope', '$scope', '$sce', '$state', 'DataUtils', 'Principal', 'HrEmployeeInfo',
        function ($rootScope, $scope, $sce, $state, DataUtils, Principal, HrEmployeeInfo) {
            $scope.isAuthenticated = Principal.isAuthenticated;
            $scope.userRole = 'ROLE_USER';

            Principal.identity().then(function (account) {
                $scope.account = account;
                if ($scope.isInArray('ROLE_ADMIN', $scope.account.authorities)) {
                    $scope.userRole = 'ROLE_ADMIN';
                    //$scope.loadAll();
                }
                else if ($scope.isInArray('ROLE_EMPLOYER', $scope.account.authorities)) {
                    $scope.userRole = 'ROLE_EMPLOYER';
                }
                else if ($scope.isInArray('ROLE_USER', $scope.account.authorities)) {
                    $scope.userRole = 'ROLE_USER';
                    $scope.loadEmployee();
                }
            });

            $scope.isInArray = function isInArray(value, array) {
                return array.indexOf(value) > -1;
            };

            $scope.loadEmployee = function () {
                console.log("loadEmployeeProfile addMode: " + $scope.addMode + ", viewMode: " + $scope.viewMode);
                HrEmployeeInfo.get({id: 'my'}, function (result) {
                    $scope.hrEmployeeInfo = result;
                    if ($scope.hrEmployeeInfo.empPhoto) {
                        var blob = $rootScope.b64toBlob($scope.hrEmployeeInfo.empPhoto, $scope.hrEmployeeInfo.empPhotoContentType);
                        $scope.filePath = (window.URL || window.webkitURL).createObjectURL(blob);
                        $scope.fileContentUrl = $sce.trustAsResourceUrl($scope.filePath);
                    }

                }, function (response) {
                    console.log("error: " + response);
                    $scope.hasProfile = false;
                    $scope.noEmployeeFound = true;
                    $scope.isSaving = false;
                })
            };


        }]);

