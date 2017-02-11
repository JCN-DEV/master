'use strict';

angular.module('stepApp')
    .controller('TimescaleApproveDialogController',
    ['$scope','$rootScope','$stateParams','AllForwaringList','TimeScaleApplication', 'entity', '$state', 'Sessions', 'InstEmployeeCode',   'Principal', 'ParseLinks', 'DataUtils','ForwardTimescaleApplication', '$modalInstance', 'StaffCount',
    function ($scope,$rootScope,$stateParams,AllForwaringList,TimeScaleApplication, entity, $state, Sessions, InstEmployeeCode,   Principal, ParseLinks, DataUtils,ForwardTimescaleApplication, $modalInstance, StaffCount) {

        $scope.approveVal = false;
        $scope.forward = {};
        TimeScaleApplication.get({id: $stateParams.id}, function(result){
            $scope.timescaleApplication = result;
            if(result.instEmployee.instLevel.name === 'MADRASA_BM' || result.instEmployee.instLevel.name === 'HSC_BM' || result.instEmployee.instLevel.name === 'Madrasha (BM)' || result.instEmployee.instLevel.name === 'HSC (BM)'){
                AllForwaringList.get({type:'BM'}, function(result){
                    $scope.forwardingList=result;
                    console.log(result);
                });
            }else{
                AllForwaringList.get({type:'VOC'}, function(result){
                    $scope.forwardingList=result;
                    console.log(result);
                });
            }

            if(result.status > 5){
                $scope.approveVal = true;
            };

        });


        $scope.confirmApprove = function(forward){
            console.log('calling method......');
            console.log(forward);

            if($scope.timescaleApplication.status > 5){
                if(Principal.hasAnyAuthority(['ROLE_DG'])){
                    $scope.timescaleApplication.dgFinalApproval=true;
                }
                if(Principal.hasAnyAuthority(['ROLE_DIRECTOR'])){
                    $scope.timescaleApplication.directorComment=$scope.remark;
                }
                TimeScaleApplication.update($scope.timescaleApplication, onSaveSuccess, onSaveError);
            }else{
                ForwardTimescaleApplication.forward({forwardTo:$scope.forward.forwardTo.code,cause: $scope.remark,memoNo: $scope.timescaleApplication.memoNo},$scope.timescaleApplication, onSaveSuccess, onSaveError);

            }

        };

        var onSaveSuccess = function(result){
            $modalInstance.close();
            $rootScope.setSuccessMessage('Application Approved Successfully.');
            $state.go('timescaleDetails', null, { reload: true });
        }
        var onSaveError = function(result){
                   console.log(result);
        }
        $scope.clear = function(){
            $modalInstance.close();
            $state.go('timescaleDetails');
        }

    }]).controller('TimescaleApproveAllDialogController',
    ['$scope','$rootScope','$stateParams','AllForwaringList','TimeScaleApplication', '$state', 'Sessions', 'InstEmployeeCode',   'Principal', 'ParseLinks', 'DataUtils','ForwardTimescaleApplication', '$modalInstance', 'StaffCount','TimescaleSummaryList',
    function ($scope,$rootScope,$stateParams,AllForwaringList,TimeScaleApplication, $state, Sessions, InstEmployeeCode,   Principal, ParseLinks, DataUtils,ForwardTimescaleApplication, $modalInstance, StaffCount, TimescaleSummaryList) {

        $scope.approveVal = false;
        $scope.forward = {};



        $scope.confirmApprove = function(){
            TimescaleSummaryList.query({}, function(result){
                console.log("comes to summarylist");
                console.log(result);
                //$scope.summaryList = result;

                angular.forEach(result, function(data){
                    console.log(data);
                    if (data.id != null) {
                        if(Principal.hasAnyAuthority(['ROLE_DG'])){
                            data.dgFinalApproval=true;
                        }
                        /*if(Principal.hasAnyAuthority(['ROLE_DIRECTOR'])){
                            $scope.timescaleApplication.directorComment=$scope.remark;
                        }*/
                        TimeScaleApplication.update(data);

                    }
                });
                $modalInstance.close();
                $state.go('timescalePendingList');
            });


        };

        var onSaveSuccess = function(result){
            $modalInstance.close();
            $rootScope.setSuccessMessage('Application Approved Successfully.');
            $state.go('timescaleDetails', null, { reload: true });
        }
        var onSaveError = function(result){
                   console.log(result);
        }
        $scope.clear = function(){
            $modalInstance.close();
            $state.go('timescalePendingList');
        }

    }]);
