'use strict';

angular.module('stepApp').controller('HrEmployeeInfoRegisterController',
    ['$scope', '$stateParams', '$state', '$q', 'entity', 'HrEmployeeInfo', 'HrDepartmentSetup', 'HrDesignationSetup', 'User','Principal','DateUtils','$translate','$timeout','Auth','MiscTypeSetupByCategory',
        function($scope, $stateParams, $state, $q, entity, HrEmployeeInfo, HrDepartmentSetup, HrDesignationSetup, User, Principal,DateUtils, $translate, $timeout, Auth, MiscTypeSetupByCategory)
        {

            console.log('Test Tear');
            $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.registerAccount = {};
        $scope.regiUser = {};
        $scope.loggedInUser =   {};

        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                    console.log("loggedInUser: "+JSON.stringify($scope.loggedInUser));
                });
            });
        };

        //$scope.getLoggedInUser();

        //$timeout(function (){angular.element('[ng-model="registerAccount.login"]').focus();});

        $scope.hrEmployeeInfo       = entity;
        $scope.hrdepartmentsetups   = HrDepartmentSetup.query({id:'bystat'});
        $scope.hrdesignationsetups  = HrDesignationSetup.query({id:'bystat'});
        $scope.workAreaList         = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});

            console.log('Test Tears : '+$scope.hrEmployeeInfo);
        $scope.generateEmployeeId = function ()
        {
            var curDt = new Date();
            //console.log(curDt);
            var uniqCode = ""+curDt.getFullYear().toString()+""+(curDt.getMonth()+1).toString()+""+curDt.getDate().toString()+""+curDt.getHours().toString()+""+curDt.getMinutes()+""+curDt.getSeconds();
            //console.log(uniqCode);
            $scope.hrEmployeeInfo.employeeId = ""+uniqCode;
        };
        $scope.generateEmployeeId();
        //$scope.users = User.query({filter: 'hremployeeinfo-is-null'});
        /*
            $q.all([$scope.hrEmployeeInfo.$promise, $scope.users.$promise]).then(function() {
            if (!$scope.hrEmployeeInfo.user || !$scope.hrEmployeeInfo.user.id) {
                return $q.reject();
            }
            return User.get({id : $scope.hrEmployeeInfo.user.id}).$promise;
        }).then(function(user) {
            $scope.users.push(user);
        }); */
        $scope.load = function(id) {
            HrEmployeeInfo.get({id : id}, function(result) {
                $scope.hrEmployeeInfo = result;
            });
        };

        $scope.calendar = {
            opened: {},
            dateFormat: 'dd-MM-yyyy',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmployeeInfoUpdate', result);
            $scope.isSaving = false;
            //$state.go('hrEmployeeInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };
        $scope.save = function ()
        {
            console.log("Register User and Saving Employee ");
            $scope.registerEmployeeUser();

            // for testing purpose.
            //$scope.registerAccount.login = 'admin';
            //$scope.saveEmployeeInfo();
        };

        $scope.saveEmployeeInfo = function()
        {
            console.log("login: "+$scope.registerAccount.login);
            User.get({login: $scope.registerAccount.login}, function (usrResult)
            {
                $scope.hrEmployeeInfo.emailAddress  = $scope.registerAccount.email;
                $scope.regiUser = usrResult;
                console.log("registerdUserInner: "+JSON.stringify(usrResult)+"\n, email: "+$scope.hrEmployeeInfo.emailAddress);

                //$scope.regiUser = result; // for the time being
                $scope.isSaving = true;
                $scope.hrEmployeeInfo.updateBy = $scope.regiUser.id;
                $scope.hrEmployeeInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

                if ($scope.hrEmployeeInfo.id != null)
                {
                    $scope.hrEmployeeInfo.logId = 0;
                    $scope.hrEmployeeInfo.logStatus = 1;
                    HrEmployeeInfo.update($scope.hrEmployeeInfo, onSaveSuccess, onSaveError);
                }
                else
                {
                    $scope.hrEmployeeInfo.logId     = 0;
                    $scope.hrEmployeeInfo.logStatus = 1;
                    $scope.hrEmployeeInfo.user      = $scope.regiUser;
                    $scope.hrEmployeeInfo.createBy  = $scope.regiUser.id;
                    $scope.hrEmployeeInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    HrEmployeeInfo.save($scope.hrEmployeeInfo, onSaveSuccess, onSaveError);
                }
            });
        };

        $scope.registerEmployeeUser = function ()
        {
            $scope.registerAccount.langKey = $translate.use();
            $scope.doNotMatch = null;
            $scope.error = null;
            $scope.errorUserExists = null;
            $scope.errorEmailExists = null;
            $scope.registerAccount.authorities = ["ROLE_USER"];
            $scope.registerAccount.activated = true;
            $scope.registerAccount.firstName = $scope.hrEmployeeInfo.fullName;
            $scope.hrEmployeeInfo.emailAddress  = $scope.registerAccount.email;
            console.log("FirstName; "+$scope.registerAccount.firstName+", login: "+$scope.registerAccount.login);

            Auth.createHrmAccount($scope.registerAccount).then(function ()
            {
                $scope.success = 'OK';
                console.log("User Login Success...");
                $scope.saveEmployeeInfo(); // call employee saving
            }).catch(function (response)
            {
                $scope.success = null;
                if (response.status === 400 && response.data === 'login already in use') {
                    $scope.errorUserExists = 'ERROR';
                } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                    $scope.errorEmailExists = 'ERROR';
                } else {
                    $scope.error = 'ERROR';
                }
            });

        };

        $scope.clear = function() {
        };

        $scope.setDefaultValue = function () {
            $scope.hrEmployeeInfo = {
                fullName: 'Yousuf Zaman',
                fullNameBn: 'Yousuf Bn',
                fatherName: 'Abdul Bari',
                motherName: 'NNahar Bg',
                birthDate: '2016-01-01',
                apointmentGoDate: '2016-01-01',
                presentId: 'PresId',
                nationalId: '9999999999999999',
                emailAddress: 'redikod@yahoo.com',
                mobileNumber: '01972419696',
                logId:0,
                logStatus:1,
                logComments:'',
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
}]);
