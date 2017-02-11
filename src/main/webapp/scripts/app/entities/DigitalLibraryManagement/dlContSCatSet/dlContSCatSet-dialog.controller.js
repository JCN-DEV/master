'use strict';

angular.module('stepApp').controller('DlContSCatSetDialogController',
    ['$scope', '$rootScope','$state', '$stateParams', 'entity', 'DlContSCatSet', 'DlContTypeSet', 'DlContCatSet', 'DlContSCatSetByName', 'DlContSCatSetByCode','FindActivcategory',
        function($scope, $rootScope, $state, $stateParams, entity, DlContSCatSet, DlContTypeSet, DlContCatSet,DlContSCatSetByName,DlContSCatSetByCode,FindActivcategory) {

        $scope.dlContSCatSet = entity;
        //$scope.dlContSCatSet.pStatus = true;
        $scope.dlconttypesets = DlContTypeSet.query();
        $scope.dlcontcatsets = DlContCatSet.query();

            $scope.load = function(id) {
            DlContSCatSet.get({id : id}, function(result) {
                $scope.dlContSCatSet = result;
            });
        };

            if($stateParams.id != null){
                DlContSCatSet.get({id : $stateParams.id}, function(result) {
                    $scope.dlContSCatSet = result;
/*
                        $scope.dlContSCatSet.dlContCatSet=result.dlContCatSet;
*/

                });
            }
           /* DlContSCatSetByName.get({name: $scope.dlContSCatSet.name}, function (dlContSCatSetBy) {

                $scope.message = "The  File Type is already exist.";

            });
            DlContSCatSetByCode.get({code: $scope.dlContSCatSet.code}, function (dlContSCatSetBy) {

                $scope.message = "The  File Type is already exist.";

            });*/


            var allDlContCatSet= FindActivcategory.query({page: $scope.page, size: 500}, function(result, headers) { return result;});


            $scope.updatedDlContCatSet=function(select){
                console.log(select.id);
                console.log("come to s cat set");

                $scope.dlcontcatsets=[];
                angular.forEach(allDlContCatSet, function(dlContCatSet) {
                    console.log(dlContCatSet.dlContTypeSet);
                    if(select.id==dlContCatSet.dlContTypeSet.id){
                        $scope.dlcontcatsets.push(dlContCatSet);
                    }
                });
            };










        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlContSCatSetUpdate', result);
            $scope.isSaving = false;
            $state.go('libraryInfo.dlContSCatSet',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlContSCatSet.id != null) {
                if($scope.dlContSCatSet.pStatus ==null){

                    $scope.dlContSCatSet.pStatus = true;
                }
                DlContSCatSet.update($scope.dlContSCatSet, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.dlContSCatSet.updated');

            } else {
                if($scope.dlContSCatSet.pStatus ==null){

                    $scope.dlContSCatSet.pStatus = true;
                }
                DlContSCatSet.save($scope.dlContSCatSet, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.dlContSCatSet.created');
            }
        };
}]);
