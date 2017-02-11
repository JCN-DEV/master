'use strict';

angular.module('stepApp').controller('AlmLeavGrpTypeMapDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'AlmLeavGrpTypeMap', 'User', 'Principal', 'DateUtils', 'AlmLeaveGroup', 'AlmLeaveType',
        function($scope, $stateParams, $state, entity, AlmLeavGrpTypeMap, User, Principal, DateUtils, AlmLeaveGroup, AlmLeaveType) {

            $scope.almLeavGrpTypeMap = entity;
            $scope.almleavegroups = [];
            $scope.almleavetypes = [];


            AlmLeaveGroup.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almleavegroups.push(dtoInfo);
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
                AlmLeavGrpTypeMap.get({id : id}, function(result) {
                    $scope.almLeavGrpTypeMap = result;
                });
            };

            $scope.isExitsData = true;
            $scope.duplicateCheckByGroupAndType = function(){
                $scope.isExitsData = true;
                AlmLeavGrpTypeMap.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.almLeaveGroup.id == $scope.almLeavGrpTypeMap.almLeaveGroup.id && dtoInfo.almLeaveType.id == $scope.almLeavGrpTypeMap.almLeaveType.id){
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
                $scope.$emit('stepApp:almLeavGrpTypeMapUpdate', result);
                $scope.isSaving = false;
                $state.go("almLeavGrpTypeMap");
            };

            $scope.save = function () {
                $scope.almLeavGrpTypeMap.updateBy = $scope.loggedInUser.id;
                $scope.almLeavGrpTypeMap.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almLeavGrpTypeMap.id != null) {
                    AlmLeavGrpTypeMap.update($scope.almLeavGrpTypeMap, onSaveFinished);
                } else {
                    $scope.almLeavGrpTypeMap.createBy = $scope.loggedInUser.id;
                    $scope.almLeavGrpTypeMap.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmLeavGrpTypeMap.save($scope.almLeavGrpTypeMap, onSaveFinished);
                }
            };

        }]);



