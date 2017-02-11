'use strict';

angular.module('stepApp')
    .controller('CurriculumMpoDateChangeDialogController',
    ['$scope','$stateParams','$rootScope', '$modalInstance','$state','IisCurriculumInfo','IisCurriculumInfoMpo',
    function ($scope,$stateParams,$rootScope, $modalInstance, $state, IisCurriculumInfo, IisCurriculumInfoMpo) {

        $scope.iisCurriculumInfo = {};

        IisCurriculumInfo.get({id : $stateParams.id}, function(result){
            $scope.iisCurriculumInfo = result;
        });

            $scope.confirmApprove = function(){
                console.log('comes to update mpo date');
                if($scope.iisCurriculumInfo.id != null){
                    console.log('comes to if');
                    IisCurriculumInfoMpo.update($scope.iisCurriculumInfo, onSaveSuccess, onSaveError);
                }
        };

        var onSaveSuccess = function(result){
            /*if(result.vacancyRoleApplied == null || result.vacancyRoleApplied == false){
                console.log('safasdf');
                InstVacancySpecialRole.apply(result);
            }
*/
            $modalInstance.close();
            $rootScope.setSuccessMessage('MPO Date Updated Successfully.');
            $state.go('instituteInfo.mpoEnlistedCurriculum', null, { reload: true });
        };

        var onSaveError = function(result){
                   console.log(result);
        };
        $scope.clear = function(){
            $modalInstance.close();
            window.history.back();

        }

    }]);
