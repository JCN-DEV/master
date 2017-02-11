'use strict';

angular.module('stepApp').controller('CmsCurriculumDialogController',
['$scope', '$stateParams', '$state', 'entity', 'CmsCurriculum', 'CmsCurriculumByCode',
        function($scope, $stateParams, $state, entity, CmsCurriculum, CmsCurriculumByCode) {

        $scope.message = '';
        $scope.cmsCurriculum = entity;

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
            $scope.isSaving = true;
            if ($scope.cmsCurriculum.id != null) {
                /*console.log($scope.cmsCurriculum);*/
                CmsCurriculum.update($scope.cmsCurriculum,onSaveSuccess, onSaveError);

            } else {
                CmsCurriculum.save($scope.cmsCurriculum, onSaveSuccess, onSaveError);
                                                        console.log($scope.cmsCurriculum);
                /*CmsCurriculumByCode.get({code: $scope.cmsCurriculum.code}, function (cmsCurriculum) {

                        console.log('exist');
                        $scope.message = "This Code is already existed.";
                    },
                    function (response) {
                        if (response.status === 404) {
                        console.log('not exist');
                            CmsCurriculum.save($scope.cmsCurriculum, onSaveSuccess, onSaveError);
                                            console.log($scope.cmsCurriculum);
                        }
                    }
                );*/
            }

            /*else {
                            CmsCurriculumByName.get({name: $scope.cmsCurriculum.name}, function (cmsCurriculum) {

                                    console.log('exist');
                                    $scope.message = "This Name is already existed.";
                                },
                                function (response) {
                                    if (response.status === 404) {
                                    console.log('not exist');
                                        CmsCurriculum.save($scope.cmsCurriculum, onSaveSuccess, onSaveError);
                                                        console.log($scope.cmsCurriculum);
                                    }
                                }
                            );
                        }*/

        };

        $scope.clear = function() {
           $scope.cmsCurriculum = null;
        };
}]);
