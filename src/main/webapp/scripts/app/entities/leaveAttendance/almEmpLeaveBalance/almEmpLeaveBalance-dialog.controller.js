'use strict';

angular.module('stepApp').controller('AlmEmpLeaveBalanceDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'AlmEmpLeaveBalance', 'User', 'Principal', 'DateUtils', 'HrEmployeeInfo', 'AlmLeaveType',
        function($scope, $stateParams, $state, entity, AlmEmpLeaveBalance, User, Principal, DateUtils, HrEmployeeInfo, AlmLeaveType) {

            $scope.almEmpLeaveBalance = entity;
            $scope.hremployeeinfos = [];
            $scope.almleavetypes = [];

            HrEmployeeInfo.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.hremployeeinfos.push(dtoInfo);
                    }
                });
            });

            AlmLeaveType.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almleavetypes.push(dtoInfo);
                    }
                });
            });

            $scope.load = function(id) {
                AlmEmpLeaveBalance.get({id : id}, function(result) {
                    $scope.almEmpLeaveBalance = result;
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

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:almEmpLeaveBalanceUpdate', result);
                $scope.isSaving = false;
                $state.go("almEmpLeaveBalance");
            };

            $scope.save = function () {
                $scope.almEmpLeaveBalance.updateBy = $scope.loggedInUser.id;
                $scope.almEmpLeaveBalance.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almEmpLeaveBalance.id != null) {
                    AlmEmpLeaveBalance.update($scope.almEmpLeaveBalance, onSaveFinished);
                } else {
                    $scope.almEmpLeaveBalance.createBy = $scope.loggedInUser.id;
                    $scope.almEmpLeaveBalance.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmEmpLeaveBalance.save($scope.almEmpLeaveBalance, onSaveFinished);
                }
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

        }]);


