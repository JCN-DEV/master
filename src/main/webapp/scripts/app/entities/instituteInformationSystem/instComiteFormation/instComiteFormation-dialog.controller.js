'use strict';

angular.module('stepApp').controller('InstComiteFormationDialogController',
    ['$scope', '$rootScope', '$state', '$stateParams', 'entity', 'InstComiteFormation', 'InstMemShip','CurrentInstCommitteeMemberByEmail',
        function($scope, $rootScope, $state, $stateParams, entity, InstComiteFormation, InstMemShip,CurrentInstCommitteeMemberByEmail) {

        $scope.instComiteFormation = entity;
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
        $scope.instmemships = InstMemShip.query();
        $scope.load = function(id) {
            InstComiteFormation.get({id : id}, function(result) {
                $scope.instComiteFormation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instComiteFormationUpdate', result);
           // $modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('instituteInfo.instComiteFormation',{},{reload:true});

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instComiteFormation.id != null) {
                InstComiteFormation.update($scope.instComiteFormation, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.instComiteFormation.updated');
            } else {
                InstComiteFormation.save($scope.instComiteFormation, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instComiteFormation.created');
            }
        };

            /*$scope.searchCommitteePresident = function searchCommitteePresident()
            {
                console.log('>>>>>>>>>>><<<<<<<<<<<<<<<<<<<' +$scope.personEmail);
                CurrentInstCommitteeMemberByEmail.get({email: $scope.personEmail}, function(result){
                    console.log(result);
                    $scope.instComiteFormation.instMemShip = result;
                } );
                *//*MpoCommitteeOneByEmail.get( {email: $scope.persnonEmail}, function(result)
                 {
                 $scope.searchCommittee = result;
                 console.log('***********************<<<<<<<<<>>>>>>>>>>>');
                 }, function(response) {
                 console.log("Error with status code", response.status);	bibi@yopmail.com
                 if(response.status == 404){
                 $rootScope.setErrorMessage('Member Not found!');
                 console.log('Member Not found!');
                 $scope.searchCommittee = {};

                 }
                 });*//*
            };*/

        $scope.clear = function() {
          //  $modalInstance.dismiss('cancel');
        };
}]);
