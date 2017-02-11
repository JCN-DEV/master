'use strict';

angular.module('stepApp').controller('InstVacancyDialogController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstVacancy', 'Institute', 'CmsTrade', 'CmsSubject', 'InstEmplDesignation','IisTradesOfCurrentInst','CmsSubAssignByTrade','$state','ActiveInstEmplDesignationByType','Principal','IisTradesByInstitute',
        function($scope, $rootScope, $stateParams, entity, InstVacancy, Institute, CmsTrade, CmsSubject, InstEmplDesignation,IisTradesOfCurrentInst,CmsSubAssignByTrade,$state,ActiveInstEmplDesignationByType, Principal, IisTradesByInstitute) {

        $scope.instVacancy = entity;

        if(Principal.hasAnyAuthority(['ROLE_ADMIN'])){
            IisTradesByInstitute.query({id: $stateParams.instituteId}, function(result){
                $scope.cmstrades = result;
            });
        }else{
            $scope.cmstrades = IisTradesOfCurrentInst.query();
        }
        $scope.cmsSubjects = [];
        //$scope.instempldesignations = InstEmplDesignation.query();
        $scope.instempldesignations = ActiveInstEmplDesignationByType.query({type: 'Employee'});
        $scope.load = function(id) {
            InstVacancy.get({id : id}, function(result) {
                $scope.instVacancy = result;
            });
        };

        var onSaveSuccess = function (result) {
            //$scope.$emit('stepApp:instVacancyUpdate', result);
            if(Principal.hasAnyAuthority(['ROLE_ADMIN'])){
                $state.go('instVacancyAdmin',{},{reload:true});
            }else{
                $state.go('instVacancy',{},{reload:true});
            }

           // $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instVacancy.id != null) {
                InstVacancy.update($scope.instVacancy, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.instVacancy.updated');
            } else {
                if(Principal.hasAnyAuthority(['ROLE_ADMIN'])){
                    Institute.get({id: $stateParams.instituteId}, function(result){
                        $scope.instVacancy.institute =result;
                        InstVacancy.save($scope.instVacancy, onSaveSuccess, onSaveError);
                    });

                }else{
                    InstVacancy.save($scope.instVacancy, onSaveSuccess, onSaveError);
                }

                $rootScope.setSuccessMessage('stepApp.instVacancy.created');
            }
        };

        $scope.setSubjects = function (id) {
            console.log('function called');
            $scope.cmsSubjects = CmsSubAssignByTrade.query({id:id}, function(result){
                console.log(result);
            });
        };

        $scope.clear = function() {
           // $modalInstance.dismiss('cancel');
        };
}]);
