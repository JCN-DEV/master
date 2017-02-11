'use strict';

angular.module('stepApp')
    .controller('InfoCorrectApproveDialogController',
     ['$scope','$rootScope', '$stateParams', '$state', 'Sessions', 'Principal', 'ParseLinks', 'DataUtils', '$modalInstance','InformationCorrection',
     function ($scope,$rootScope, $stateParams, $state, Sessions, Principal, ParseLinks, DataUtils, $modalInstance,InformationCorrection) {

         $scope.approveVal = false;
         $scope.forward = {};
         InformationCorrection.get({id: $stateParams.id}, function(result){
             $scope.informationCorrection = result;

         });

         $scope.confirmApprove = function(){
             if($scope.informationCorrection.status > 6){
                 if(Principal.hasAnyAuthority(['ROLE_DG'])){
                     $scope.informationCorrection.dgFinalApproval=true;
                 }
                 if(Principal.hasAnyAuthority(['ROLE_DIRECTOR'])){
                     $scope.informationCorrection.directorComment=$scope.remark;
                 }

             }
             InformationCorrection.update($scope.informationCorrection, onSaveSuccess, onSaveError);
         };

         var onSaveSuccess = function(result){
             $modalInstance.close();
             $state.go('informationCorrection.detail', null, { reload: true });
             $rootScope.setSuccessMessage('Application Approved Successfully.');
         }
         var onSaveError = function(result){
             console.log(result);
         }
         $scope.clear = function(){
             $modalInstance.close();
             $state.go('informationCorrection.detail');
         }

    }]);
