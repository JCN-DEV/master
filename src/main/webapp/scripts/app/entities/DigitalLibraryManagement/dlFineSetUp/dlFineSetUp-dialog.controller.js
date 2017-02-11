'use strict';

angular.module('stepApp').controller('DlFineSetUpDialogController',
    ['$scope','$state', '$stateParams', 'entity', 'DlFineSetUp', 'DlContSCatSet','DlContTypeSet','DlContCatSet','FindActivcategory','FindActiveSubcategory','DlFineInfoBySCatId',
        function($scope,$state, $stateParams, entity, DlFineSetUp, DlContSCatSet,DlContTypeSet,DlContCatSet,FindActivcategory,FindActiveSubcategory,DlFineInfoBySCatId) {

        $scope.dlFineSetUp = entity;
        $scope.dlcontscatsets = DlContSCatSet.query();
            $scope.dlFineSetUp.status=true;
            $scope.buttondisabled=false;
        $scope.load = function(id) {
            DlFineSetUp.get({id : id}, function(result) {
                $scope.dlFineSetUp = result;
            });
        };
            $scope.subcatValidationbyScatId =function(getSCatId) {
                $scope.subcategoryId=getSCatId.id;
                console.log($scope.subcategoryId);

                DlFineInfoBySCatId.get({id:  $scope.subcategoryId}, function (result) {
                    $scope.fineResult=result;
                    console.log($scope.fineResult);
                    if($scope.fineResult){
                        $scope.errormsg="This Sub-category is Already exist";
                        $scope.buttondisabled=true;
                    }
                }, function (respons) {

                    if (respons.status === 404) {
                        console.log("This Sub-category is ok")

                        $scope.buttondisabled=false;
                        $scope.errormsg = 'Accepted';


                    }
                });
            };
            $scope.dlContTypeSets = DlContTypeSet.query();
            var allDlContCatSet = FindActivcategory.query({page: $scope.page, size: 65}, function (result, headers) {
                return result;
            });
            var allDlContSCatSet = FindActiveSubcategory.query({page: $scope.page, size: 500}, function (result, headers) {
                return result;
            });

            $scope.updatedDlContSCatSet = function (select) {
                /* console.log("selected district .............");
                 console.log(select);*/
                $scope.dlContSCatSets = [];
                angular.forEach(allDlContSCatSet, function (dlContSCatSet) {
                    if (select.id == dlContSCatSet.dlContCatSet.id) {
                        $scope.dlContSCatSets.push(dlContSCatSet);
                    }
                });

            };

            $scope.dlContCatSets = DlContCatSet.query();
            $scope.dlContSCatSets = DlContSCatSet.query();

            $scope.updatedDlContCatSet = function (select) {
                $scope.dlContCatSets = [];
                angular.forEach(allDlContCatSet, function (dlContCatSet) {

                    if ((dlContCatSet.dlContTypeSet && select) && (select.id != dlContCatSet.dlContTypeSet.id)) {
                        console.log("There is error");
                    } else {
                        console.log("There is the fire place");
                        $scope.dlContCatSets.push(dlContCatSet);
                    }
                });
            };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlFineSetUpUpdate', result);
            $scope.isSaving = false;
             $state.go('libraryInfo.dlFineSetUp',{},{reload:true});

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlFineSetUp.id != null) {
                DlFineSetUp.update($scope.dlFineSetUp, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.dlFineSetUp.updated');

            } else {
                DlFineSetUp.save($scope.dlFineSetUp, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.dlFineSetUp.created');

            }
        };

        $scope.clear = function() {
        };
}]);
