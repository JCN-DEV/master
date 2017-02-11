'use strict';

angular.module('stepApp').controller('AlmEmpLeaveGroupMapDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'AlmEmpLeaveGroupMap', 'User', 'Principal', 'DateUtils', 'HrEmployeeInfo', 'AlmLeaveGroup',
        function($scope, $stateParams, $state, entity, AlmEmpLeaveGroupMap, User, Principal, DateUtils, HrEmployeeInfo, AlmLeaveGroup) {

            $scope.almEmpLeaveGroupMap = entity;
            $scope.hremployeeinfos = [];
            $scope.almleavegroups = [];

            HrEmployeeInfo.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.hremployeeinfos.push(dtoInfo);
                    }
                });
            });

            AlmLeaveGroup.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almleavegroups.push(dtoInfo);
                    }
                });
            });


            $scope.load = function(id) {
                AlmEmpLeaveGroupMap.get({id : id}, function(result) {
                    $scope.almEmpLeaveGroupMap = result;
                });
            };
            $scope.isExitsData = true;
            $scope.duplicateCheckByGroupAndEmployee = function(){
                $scope.isExitsData = true;
                AlmEmpLeaveGroupMap.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.employeeInfo.id == $scope.almEmpLeaveGroupMap.employeeInfo.id
                            && dtoInfo.almLeaveGroup.id == $scope.almEmpLeaveGroupMap.almLeaveGroup.id
                            && dtoInfo.activeStatus){
                            $scope.isExitsData = false;
                        }
                    });
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
                $scope.$emit('stepApp:almEmpLeaveGroupMapUpdate', result);
                $scope.isSaving = false;
                $state.go("hrm.leavecanform");
            };

            $scope.save = function () {
                $scope.almEmpLeaveGroupMap.updateBy = $scope.loggedInUser.id;
                $scope.almEmpLeaveGroupMap.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almEmpLeaveGroupMap.id != null) {
                    AlmEmpLeaveGroupMap.update($scope.almEmpLeaveGroupMap, onSaveFinished);
                } else {
                    $scope.almEmpLeaveGroupMap.createBy = $scope.loggedInUser.id;
                    $scope.almEmpLeaveGroupMap.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmEmpLeaveGroupMap.save($scope.almEmpLeaveGroupMap, onSaveFinished);
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



