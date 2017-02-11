'use strict';

angular.module('stepApp')
    .controller('InstMpoCodeChangeDialogController',
    ['$scope','$stateParams','$rootScope', '$modalInstance','$state','Institute','InstVacancyGeneralRole',
    function ($scope,$stateParams,$rootScope, $modalInstance, $state, Institute, InstVacancyGeneralRole) {

        $scope.institute = {};

        Institute.get({id : $stateParams.id}, function(result){
            $scope.institute = result;
        });

            $scope.confirmApprove = function(){

                if($scope.institute.id != null){
                    Institute.update($scope.institute, onSaveSuccess, onSaveError);
                }
        };

        var onSaveSuccess = function(result){
            if(result.vacancyAssigned == null || result.vacancyAssigned == false){
                console.log('safasdf');
                InstVacancyGeneralRole.save(result);
            }

            $modalInstance.close();
            $rootScope.setSuccessMessage('MPO Code Updated Successfully.');
            $state.go('instituteInfo.mpoEnlistedInst', null, { reload: true });
        };

        var onSaveError = function(result){
                   console.log(result);
        };
        $scope.clear = function(){
            $modalInstance.close();
            window.history.back();

        }

    }]);
