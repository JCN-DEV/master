'use strict';

angular.module('stepApp').controller('AlmLeaveRuleDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'AlmLeaveRule', 'User', 'Principal', 'DateUtils', 'AlmLeaveType', 'AlmEarningFrequency', 'AlmEarningMethod', '$http', 'AlmLeavGrpTypeMap',
        function($scope, $rootScope, $stateParams, $state, entity, AlmLeaveRule, User, Principal, DateUtils, AlmLeaveType, AlmEarningFrequency, AlmEarningMethod, $http, AlmLeavGrpTypeMap) {

            $scope.almLeaveRule = entity;
            $scope.almleavetypes = [];
            $scope.almEarningFrequencys = [];
            $scope.almEarningMethods = [];
            //$scope.almleavegroups = [];

            AlmLeaveType.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almleavetypes.push(dtoInfo);
                    }
                });
            });

            /*AlmLeaveGroup.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almleavegroups.push(dtoInfo);
                    }
                });
            });*/

            AlmEarningMethod.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almEarningMethods.push(dtoInfo);
                    }
                });
            });

            AlmEarningFrequency.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almEarningFrequencys.push(dtoInfo);
                    }
                });
            });

            $scope.load = function(id) {
                AlmLeaveRule.get({id : id}, function(result) {
                    $scope.almLeaveRule = result;
                });
            };

            /*$scope.loadLeaveTypeByLeaveGroup = function(){
                $scope.almleavetypes = [];
                AlmLeavGrpTypeMap.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.almLeaveGroup.id == $scope.almLeaveRule.almLeaveGroup.id){
                            $scope.almleavetypes.push(dtoInfo.almLeaveType);
                        }
                    });
                });
            };*/

            $scope.isExitsData = true;
            $scope.duplicateCheckByGroupAndType = function(){
                $scope.isExitsData = true;
                AlmLeaveRule.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.almLeaveType.id == $scope.almLeaveRule.almLeaveType.id){
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
                $scope.$emit('stepApp:almLeaveRuleUpdate', result);
                $scope.isSaving = false;
                $state.go("almLeaveRule");
            };

            /*$scope.checkDuplicateByLeaveGroupAndType = function()

            {
                var data =
                {
                    groupId:        $scope.almLeaveRule.almLeaveGroup.id,
                    leaveTypeId:    $scope.almLeaveRule.almLeaveType.id
                };

                var config = {
                    params: data,
                    headers : {'Accept' : 'application/json'}
                };
                $http.get('api/almLeaveRulesCheckLeaveGroupAndType/', config).then(function(response)
                {
                    console.log("Success: "+JSON.stringify(response));
                    //Proces json response like: response.data.id

                }, function(response)
                {
                    console.log("Failed: "+JSON.stringify(response));
                });
            };*/

            $scope.save = function () {
                $scope.almLeaveRule.updateBy = $scope.loggedInUser.id;
                if($scope.almLeaveRule.minGapBetweenTwoLeaves == null){
                    $scope.almLeaveRule.minGapBetweenTwoLeaves = 0;
                }
                if($scope.almLeaveRule.noOfInstanceAllowed == null){
                    $scope.almLeaveRule.noOfInstanceAllowed = 0;
                }
                $scope.almLeaveRule.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almLeaveRule.id != null) {
                    AlmLeaveRule.update($scope.almLeaveRule, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.almLeaveRule.updated');
                } else {
                    $scope.almLeaveRule.createBy = $scope.loggedInUser.id;
                    $scope.almLeaveRule.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmLeaveRule.save($scope.almLeaveRule, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.almLeaveRule.created');
                }
            };

        }]);


