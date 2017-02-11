'use strict';

angular.module('stepApp')
    .controller('LoginController',
    ['$rootScope', '$scope', '$state', '$timeout', 'Auth', 'Principal', 'InstituteByLogin', 'CurrentInstEmployee', 'GetRoleAndRightsByUsedId', 'GetRoleIdByUsedId',
        function ($rootScope, $scope, $state, $timeout, Auth, Principal, InstituteByLogin, CurrentInstEmployee, GetRoleAndRightsByUsedId, GetRoleIdByUsedId) {
            $scope.users = [];
            $scope.user = {};
            $scope.errors = {};
            $rootScope.showMpoLink = true;

            $scope.rememberMe = true;
            $timeout(function () {
                angular.element('[ng-model="username"]').focus();
            });
            $scope.login = function (event) {
                event.preventDefault();
                Auth.login({
                    username: $scope.username,
                    password: $scope.password,
                    rememberMe: $scope.rememberMe
                }).then(function () {

                    //$http.get('https://api.ipify.org')
                    //    .then(function(response) {
                    //        $rootScope.clientIpAddress = response.data;
                    //        console.log($rootScope.clientIpAddress);
                    //    });

                    Principal.identity().then(function (account) {
                        $rootScope.accountName = account.desigShort;
                        console.log($rootScope.accountName);
                    });

                    GetRoleIdByUsedId.query({userId: $scope.username}, function (result) {
                        $scope.roles = result;
                        console.log(result);
                        /*
                         User RoleID, User ID, role and rights
                         */
                        if (typeof (Storage) !== "undefined") {

                            //localStorage.setItem('rolesRightsLocalStorage', JSON.stringify($scope.roles) );
                            //var rolesRightsLocalStorage = localStorage.getItem('rolesRightsLocalStorage');
                            var json = JSON.parse(JSON.stringify($scope.roles).substring(1, JSON.stringify($scope.roles).length - 1));
                            sessionStorage.setItem('rolesRights', JSON.stringify($scope.roles));
                            sessionStorage.setItem('userid', json.userid);
                            sessionStorage.setItem('username', json.username);
                            sessionStorage.setItem('userroleid', json.userroleid);

                            var rolesRights = sessionStorage.getItem('rolesRights');
                            var username = sessionStorage.getItem('username');

                            console.log("login rights : " + rolesRights);
                            console.log("login user name: " + username);


                        } else {
                            alert("Sorry, your browser does not support web storage...");
                        }
                    });


                    $scope.authenticationError = false;

                    if (Principal.hasAnyAuthority(['ROLE_EMPLOYER']))
                    {
                        $state.go('jp-dashboard');
                    }
                    else if (Principal.hasAnyAuthority(['ROLE_HRM_ADMIN']))
                    {
                        $state.go('hrm.dashboard');
                    }
                    else if (Principal.hasAnyAuthority(['ROLE_HRM_USER']))
                    {
                        $state.go('hrm');
                    }
                    else if (Principal.hasAnyAuthority(['ROLE_MPOADMIN']))
                    {
                        $state.go('mpo.dashboard');
                    }
                     else if (Principal.hasAnyAuthority(['ROLE_ADMIN']) || Principal.hasAnyAuthority(['ROLE_INSTITUTE']))
                     {
                        InstituteByLogin.query({}, function (result)
                        {
                            if (!result.mpoEnlisted) {
                                $rootScope.showMpoLink = false;
                            }
                            $state.go('instituteInfo.generalInfo');
                        });

                    } else if (Principal.hasAnyAuthority(['ROLE_ADMIN']) || Principal.hasAnyAuthority(['ROLE_INSTEMP'])) {
                        CurrentInstEmployee.get({}, function (result) {
                            if (!result.institute.mpoEnlisted) {
                                console.log('>>>>>>>>>>>. not mpo enlisted');
                                $rootScope.showMpoLink = false;
                            }
                            $state.go('employeeInfo.home');
                        });

                    } else if (Principal.hasAnyAuthority(['ROLE_ADMIN']) || Principal.hasAnyAuthority(['ROLE_MANEGINGCOMMITTEE'])) {
                        /*CurrentInstEmployee.get({},function(result){
                         if(!result.institute.mpoEnlisted){
                         console.log('>>>>>>>>>>>. not mpo enlisted');
                         $rootScope.showMpoLink = false;
                         }
                         $state.go('mpo.dashboard');
                         });*/
                        $state.go('mpo.dashboard');

                    } else if (Principal.hasAnyAuthority(['ROLE_ADMIN']) || Principal.hasAnyAuthority(['ROLE_FRONTDESK'])) {
                        $state.go('mpo.dashboard');
                    } else if (Principal.hasAnyAuthority(['ROLE_ADMIN']) || Principal.hasAnyAuthority(['ROLE_AD'])) {
                        $state.go('mpo.dashboard');
                    } else if (Principal.hasAnyAuthority(['ROLE_ADMIN']) || Principal.hasAnyAuthority(['ROLE_DIRECTOR'])) {
                        $state.go('mpo.dashboard');
                    } else if (Principal.hasAnyAuthority(['ROLE_ADMIN']) || Principal.hasAnyAuthority(['ROLE_DG'])) {
                        $state.go('mpo.dashboard');
                    } else if (Principal.hasAnyAuthority(['ROLE_MINISTRY']) || Principal.hasAnyAuthority(['ROLE_AG']) || Principal.hasAnyAuthority(['ROLE_BANK'])) {
                        $state.go('mpoSalaryFlow');
                    } else if (Principal.hasAnyAuthority(['ROLE_MINISTER'])) {
                        console.log("ROLE_MINISTER");
                        $state.go('risNewAppForm.Ministry');
                    } else if (Principal.hasAnyAuthority(['ROLE_GOVT_STUDENT'])) {
                        $state.go('home');
                    } else if (Principal.hasAnyAuthority(['ROLE_JOBSEEKER'])) {
                        $state.go('jp-dashboard');
                    } else {
                        $state.go('home');
                        console.log("ROLE_MINISTER");
                    }

                    /*if (Principal.hasAnyAuthority(['ROLE_INSTEMP'])) {
                     $state.go('mpo.employeeTrack');
                     }
                     else if (Principal.hasAnyAuthority(['ROLE_DEO'])) {
                     $state.go('mpo.application');
                     }
                     else if (Principal.hasAnyAuthority(['ROLE_FRONTDESK'])) {
                     $state.go('mpo.application');
                     }
                     else if (Principal.hasAnyAuthority(['ROLE_AD'])) {
                     $state.go('mpo.application');
                     }
                     else if (Principal.hasAnyAuthority(['ROLE_DIRECTOR'])) {
                     $state.go('mpo.application');
                     }
                     else if (Principal.hasAnyAuthority(['ROLE_DG'])) {
                     $state.go('mpo.application');
                     }
                     else if (Principal.hasAnyAuthority(['ROLE_MPOADMIN'])) {
                     $state.go('mpo.application');
                     }
                     else if (Principal.hasAnyAuthority(['ROLE_MANAGER'])) {
                     $state.go('mpo.application');
                     }
                     else if (Principal.hasAnyAuthority(['ROLE_ADMIN'])) {
                     $state.go('mpo.employeeTrack');
                     }
                     else if (Principal.hasAnyAuthority(['ROLE_EMPLOYER'])) {
                     $state.go('employer.profile');
                     }
                     else if (Principal.hasAnyAuthority(['ROLE_INSTITUTE'])) {
                     //$state.go('instituteInfo');
                     $state.go('mpo.application');
                     }
                     else {
                     $state.go('resume');
                     }*/


                    //if ($rootScope.previousStateName === 'register') {
                    //    $state.go('home');
                    //} else if ($rootScope.previousStateName === 'register') {
                    //} else {
                    //    $rootScope.back();
                    //}
                }).catch(function () {
                    $scope.authenticationError = true;
                });
            };
        }]);
