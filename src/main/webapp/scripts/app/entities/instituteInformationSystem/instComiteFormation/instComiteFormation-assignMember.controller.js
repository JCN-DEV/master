'use strict';

angular.module('stepApp').controller('InstComiteFormationAssignMemsController',
    ['$scope', '$state', '$stateParams', 'entity', 'InstComiteFormation', 'InstMemShip','CurrentInstCommitteeMemberByEmail','InstComiteFormationWith','InstComiteFormationWithCommittee',
        function($scope, $state, $stateParams, entity, InstComiteFormation, InstMemShip, CurrentInstCommitteeMemberByEmail, InstComiteFormationWith,InstComiteFormationWithCommittee) {

        //$scope.instComiteFormation = entity;

            InstComiteFormationWithCommittee.get({id : $stateParams.id}, function(result) {
                $scope.instComiteFormation = result;
                console.log(result.instMemShipsnstMemShip);
            });
        $scope.personEmail=null;
        //$scope.instmemships = InstMemShip.query();
       /* $scope.load = function(id) {
            InstComiteFormationWithCommittee.get({id : id}, function(result) {
                $scope.instComiteFormation = result;
            });
        };*/


            $scope.searchCommittee2 = function searchCommittee2()
            {
                console.log('>>>>>>>>>>><<<<<<<<<<<<<<<<<<<' +$scope.personEmail);
                CurrentInstCommitteeMemberByEmail.get({email: $scope.personEmail}, function(result){
                    console.log(result);
                    $scope.instMemShip = result;
                } );
                /*MpoCommitteeOneByEmail.get( {email: $scope.persnonEmail}, function(result)
                {
                    $scope.searchCommittee = result;
                    console.log('***********************<<<<<<<<<>>>>>>>>>>>');
                }, function(response) {
                    console.log("Error with status code", response.status);
                    if(response.status == 404){
                        $rootScope.setErrorMessage('Member Not found!');
                        console.log('Member Not found!');
                        $scope.searchCommittee = {};

                    }
                });*/
            };
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instComiteFormationUpdate', result);
           // $modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('instituteInfo.instComiteFormation',{},{reload:true});

        };
            $scope.addMember  = function addMember() {

            console.log('comes to add member method');
            console.log($scope.instComiteFormation);
            InstComiteFormationWith.update({id: $scope.instComiteFormation.id},$scope.instMemShip,onAddMember);
            /*var members = [];
                members.push($scope.instComiteFormation.instMemShipsnstMemShip);
            members.push($scope.instMemShip);
            $scope.instComiteFormation.instMemShipsnstMemShip =members;
                console.log($scope.instComiteFormation);
            InstComiteFormation.update($scope.instComiteFormation);*/

    };

        var onAddMember = function (result) {
            location.reload();
        };
            var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instComiteFormation.id != null) {
                InstComiteFormation.update($scope.instComiteFormation, onSaveSuccess, onSaveError);
            } else {
                InstComiteFormation.save($scope.instComiteFormation, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
          //  $modalInstance.dismiss('cancel');
        };
}]);
