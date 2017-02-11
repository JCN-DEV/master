'use strict';

angular.module('stepApp')
    .controller('MpoDenyDialogController',
     ['$scope','Principal','MpoApplication', 'entity', 'DateUtils', '$state', '$modalInstance','MpoApplicationDecline','MpoCommitteeDescision', 'MpoApplicationStatusLog',
     function ($scope,Principal,MpoApplication, entity, DateUtils, $state, $modalInstance,MpoApplicationDecline,MpoCommitteeDescision, MpoApplicationStatusLog) {
        $scope.mpoApplication = entity;
        $scope.mpoApplicationStatusLog = {};
        $scope.mpoCommitteeDescision = {};
        $scope.causeDeny = "";

        $scope.confirmDecline = function(){
            if(Principal.hasAnyAuthority(['ROLE_MPOCOMMITTEE']) && !Principal.hasAnyAuthority(['ROLE_DG'])){
                if($scope.mpoCommitteeDescision.id != null){
                    MpoCommitteeDescision.update($scope.mpoCommitteeDescision, onSaveSuccess, onSaveError);
                }else{
                    $scope.mpoCommitteeDescision.mpoApplication = $scope.mpoApplication;
                    $scope.mpoCommitteeDescision.status = 0;
                    $scope.mpoCommitteeDescision.comments = $scope.causeDeny;
                    MpoCommitteeDescision.save($scope.mpoCommitteeDescision, onSaveSuccess, onSaveError);
                }

            }else{
                MpoApplicationDecline.decline({id: $scope.mpoApplication.id, cause: $scope.causeDeny},{}, onSaveSuccess, onSaveError);

            }
        };
        var onSaveSuccess = function(result){
            $modalInstance.close();
        };
        var onSaveError = function(result){
                   console.log(result);
        };
        $scope.clear = function(){
            $modalInstance.close();
            window.history.back();
        };


    }]);
