'use strict';

angular.module('stepApp')
    .controller('CourseMpoDateChangeDialogController',
    ['$scope','$stateParams','$rootScope', '$modalInstance','$state','IisCourseInfo','InstVacancySpecialRole',
    function ($scope,$stateParams,$rootScope, $modalInstance, $state, IisCourseInfo, InstVacancySpecialRole) {

        $scope.iisCourseInfo = {};

        IisCourseInfo.get({id : $stateParams.id}, function(result){
            $scope.iisCourseInfo = result;
        });

            $scope.confirmApprove = function(){
                console.log('comes to update mpo date');
                if($scope.iisCourseInfo.id != null){
                    console.log('comes to if');
                    IisCourseInfo.update($scope.iisCourseInfo, onSaveSuccess, onSaveError);
                }
        };

        var onSaveSuccess = function(result){
            if(result.vacancyRoleApplied == null || result.vacancyRoleApplied == false){
                console.log('safasdf');
                InstVacancySpecialRole.apply(result);
            }

            $modalInstance.close();
            $rootScope.setSuccessMessage('MPO Date Updated Successfully.');
            $state.go('instituteInfo.mpoEnlistedCourse', null, { reload: true });
        };

        var onSaveError = function(result){
                   console.log(result);
        };
        $scope.clear = function(){
            $modalInstance.close();
            window.history.back();

        }

    }]);
