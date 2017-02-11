'use strict';

angular.module('stepApp').controller('InstAdmInfoDialogController',
    ['$scope','$rootScope', '$timeout', '$stateParams', '$q', '$state', 'entity', 'InstAdmInfo', 'Institute','governingBodys','InstGovernBody','InstituteAllInfo','InstGovernBodyTemp','InstAdmInfoTemp','InstituteAllInfosTemp',
        function($scope, $rootScope, $timeout, $stateParams, $q, $state,  entity, InstAdmInfo, Institute, governingBodys,InstGovernBody,InstituteAllInfo, InstGovernBodyTemp, InstAdmInfoTemp, InstituteAllInfosTemp) {


        $scope.instAdmInfo = entity;
        $scope.instGovernBodys = governingBodys;
        $scope.succeessMsg = '';

        $q.all([$scope.instAdmInfo.$promise]).then(function() {

            console.log($scope.instAdmInfo);
            if($scope.instAdmInfo.id == null){
                 console.log($scope.instAdmInfo.id);
            }
            else{
                InstituteAllInfosTemp.get({id: $scope.instAdmInfo.institute.id},function(result){
                    console.log(result);
                    $scope.instGovernBodys = result.instGovernBody;
                })
            }
        });

        $scope.AddMore = function(){
            $scope.instGovernBodys.push(
                {
                    name: null,
                    position: null,
                    mobileNo: null,
                    status: null
               }
            );
            $timeout(function() {
                $rootScope.refreshRequiredFields();
            }, 100);
        }
        $scope.removeGovernBody = function(GovernBody){
            var index =  $scope.instGovernBodys.indexOf(GovernBody);
            $scope.instGovernBodys.splice(index,1);

        }
        $scope.load = function(id) {
            InstAdmInfo.get({id : id}, function(result) {
                $scope.instAdmInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
console.log(result);
            $scope.$emit('stepApp:adminInfo', result);
            $scope.isSaving = false;

            angular.forEach($scope.instGovernBodys,function(data){
                if(data.id){
                    //InstGovernBody.update(data, onGoverningBodySaveSuccess, onGoverningBodySaveError);
                    InstGovernBodyTemp.update(data, onGoverningBodySaveSuccess, onGoverningBodySaveError);
                    $rootScope.setSuccessMessage('stepApp.instAdmInfo.created');
                }
                else{
                    if(data.name != null && data.position !=null){
                        data.institute =  result.institute;
                        //InstGovernBody.save(data, onGoverningBodySaveSuccess, onGoverningBodySaveError);
                        InstGovernBodyTemp.save(data, onGoverningBodySaveSuccess, onGoverningBodySaveError);
                        $rootScope.setWarningMessage('stepApp.instAdmInfo.updated');
                    }
                }

            });

            $state.go('instituteInfo.adminInfo',{},{reload:true});
        };

        $scope.mobileValidation = function(){

        };

        var onSaveError = function (result) {
            console.log("error check");
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instAdmInfo.id != null) {
                if($scope.instAdmInfo.status==1){
                    console.log('  after approved');
                    $scope.instAdmInfo.status=3;
                    $scope.succeessMsg = 'stepApp.instAdmInfo.updated';
                    InstAdmInfo.update($scope.instAdmInfo, onSaveSuccess, onSaveError);


                }else{
                    $scope.instAdmInfo.status=0;
                    console.log('  before approved');
                    $scope.succeessMsg = 'stepApp.instAdmInfo.updated';
                    //InstAdmInfo.update($scope.instAdmInfo, onSaveSuccess, onSaveError);
                    InstAdmInfoTemp.update($scope.instAdmInfo, onSaveSuccess, onSaveError);

                }

            } else {
                $scope.instAdmInfo.status = 0;
                $scope.succeessMsg = 'stepApp.instAdmInfo.created';
                //InstAdmInfo.save($scope.instAdmInfo, onSaveSuccess, onSaveError);
                InstAdmInfoTemp.save($scope.instAdmInfo, onSaveSuccess, onSaveError);


            }
        };


        var onGoverningBodySaveSuccess = function(result){
            $scope.isSaving = false;
            $rootScope.setWarningMessage($scope.succeessMsg);
            $state.go('instituteInfo.adminInfo',{},{reload:true});

        }
        var onGoverningBodySaveError = function(result){
            console.log("error check");
            $scope.isSaving = false;
        }

        $scope.clear = function() {
             $scope.instAdmInfo = null;
        };
}]);
