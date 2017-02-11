'use strict';

angular.module('stepApp')
    .controller('InfoCorrectDenyDialogController',
     ['$scope','Principal', 'entity', '$stateParams', '$state', '$modalInstance','InformationCorrectionDecline',
     function ($scope,Principal, entity, $stateParams, $state, $modalInstance,InformationCorrectionDecline) {
        //$scope.informationCorrection = entity;
        $scope.causeDeny = "";

        $scope.confirmDecline = function(){
            InformationCorrectionDecline.decline({id: $stateParams.id, cause: $scope.causeDeny},{}, onSaveSuccess, onSaveError);
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
