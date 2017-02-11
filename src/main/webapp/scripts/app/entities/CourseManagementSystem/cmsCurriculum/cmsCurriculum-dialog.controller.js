'use strict';

angular.module('stepApp').controller('CmsCurriculumDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'CmsCurriculum',
        function($scope, $stateParams, $state, entity, CmsCurriculum) {

        $scope.message = '';
        $scope.cmsCurriculum = entity;
        $scope.cmsCurriculum.duration_type= "Semester";

        $scope.cmsCurriculum.status=true;


        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cmsCurriculumUpdate', result);
            /*$modalInstance.close(result);*/
            $scope.isSaving = false;
           $state.go('courseInfo.curriculum',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };



         $scope.save = function () {
         console.log('comes to save ');
                $scope.isSaving = true;
                if ($scope.cmsCurriculum.id != null) {
                    CmsCurriculum.update($scope.cmsCurriculum, onSaveSuccess, onSaveError);
                } else {
                    CmsCurriculum.save($scope.cmsCurriculum, onSaveSuccess, onSaveError);
                }
            };


         $scope.uniqueError = false;

         /*$scope.checkCurriculumUniqueness = function () {
                    console.log($scope.cmsCurriculum.code);
                    console.log($scope.cmsCurriculum.name);
                    if ($scope.cmsCurriculum.code != null && $scope.cmsCurriculum.name != null) {
                        CmsCurriculumByCodeAndName.get({code: $scope.cmsCurriculum.code, name: $scope.cmsCurriculum.name}, function (result) {
                                console.log('exist');
                                $scope.uniqueError = true;
                            },
                            function (response) {
                                if (response.status === 404) {
                                    console.log('not exist');
                                    $scope.uniqueError = false;
                                }
                            }
                        );
                    }
                };*/

        $scope.clear = function() {
           $scope.cmsCurriculum = null;
        };
}]);
