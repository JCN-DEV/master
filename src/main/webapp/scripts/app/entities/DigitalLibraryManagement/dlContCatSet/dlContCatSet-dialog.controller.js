'use strict';

angular.module('stepApp').controller('DlContCatSetDialogController',
    ['$scope', '$rootScope','$state', '$stateParams', 'entity', 'DlContCatSet', 'DlContTypeSet','DlContCatSetByName', 'DlContCatSetByCode',
        function($scope, $rootScope, $state, $stateParams, entity, DlContCatSet, DlContTypeSet,DlContCatSetByName,DlContCatSetByCode) {

        $scope.dlContCatSet = entity;
        //$scope.dlContCatSet.pStatus = true;
        $scope.dlconttypesets = DlContTypeSet.query({size:1000});

            //DlContCatSet.get({id : $stateParams.id});
        $scope.load = function(id) {
            DlContCatSet.get({id : id}, function(result) {
                $scope.dlContCatSet = result;
            });
        };
        if($stateParams.id != null){
            DlContCatSet.get({id : $stateParams.id}, function(result) {
                $scope.dlContCatSet = result;
            });
        }

            //DlContCatSetByName.get({name: $scope.dlContCatSet.name}, function (dlContCatSet) {
            //
            //                  $scope.message = "The  File Type is already exist.";
            //
            //              });
            //DlContCatSetByCode.get({code: $scope.dlContCatSet.code}, function (dlContCatSet) {
            //
            //    $scope.message = "The  File Type is already exist.";
            //
            //});
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlContCatSetUpdate', result);
            $scope.isSaving = false;
            $state.go('libraryInfo.dlContCatSet',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlContCatSet.id != null) {
                if($scope.dlContCatSet.pStatus ==null){

                    $scope.dlContCatSet.pStatus = true;
                }
                DlContCatSet.update($scope.dlContCatSet, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.dlContCatSet.updated');
            } else {
                if($scope.dlContCatSet.pStatus ==null){
                    $scope.dlContCatSet.pStatus = true;
                }
                DlContCatSet.save($scope.dlContCatSet, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.dlContCatSet.created');

            }
        };
}]);
