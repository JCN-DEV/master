'use strict';

angular.module('stepApp')
	.controller('dlBookRequisitionApproveController',
	['$scope','$state','$stateParams', '$rootScope', '$modalInstance', 'entity', 'DlBookIssue','DlBookEdition',
	function($scope,$state,$stateParams, $rootScope, $modalInstance, entity, DlBookIssue,DlBookEdition) {

        $scope.dlBookIssue = {};
        $scope.dlBookEditions = DlBookEdition.query();
        $scope.issueSave=false;
/*
       DlContUpld.get({id: $stateParams.id}, function (result) {
*/

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

                    DlBookIssue.get({id: $stateParams.id}, function(result) {
                        $scope.dlBookIssue = result;
                        $scope.editionId=$scope.dlBookIssue.dlBookEdition.id;
                       console.log("amar data"+$scope.editionId);

                    });

        $scope.confirmApprove = function () {
             $scope.dlBookIssue.status = 1;
             $scope.dlBookIssue.issueDate=new Date();
             DlBookIssue.update($scope.dlBookIssue, onSaveSuccess);

        };
        $scope.getEditionInfo=function(data){
                    $scope.answer=data;
                    console.log($scope.answer);
                    console.log("edition list");

                    if($scope.answer.totalCopies > 1){
                        $scope.issueSave=false;
                        console.log("Book available");
                       $scope.availError = 'Your requested Book is  Available';
                    }else{
                    $scope.issueSave=true;
                    console.log("Book is not available");
                    $scope.availError = 'Your requested Book is not Available';
                    }

                }

        var onSaveSuccess = function (result) {

                DlBookEdition.get({id: $scope.editionId}, function(result3){
                  $scope.dlBookEdition =  result3;
                  $scope.dlBookEdition.totalCopies = parseInt($scope.dlBookEdition.totalCopies-1);
                  DlBookEdition.update($scope.dlBookEdition)
                });
            $modalInstance.close(true);
            $state.go('libraryInfo.dlContUpldByUser', null, { reload: true });
        };

    }]);
