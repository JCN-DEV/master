'use strict';

angular.module('stepApp').controller('CmsSemesterDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'CmsSemester', 'CmsCurriculum', 'CmsSemesterByCodeAndName','FindActivecmsCurriculums',
    function ($scope, $stateParams, $state, entity, CmsSemester, CmsCurriculum, CmsSemesterByCodeAndName,FindActivecmsCurriculums) {

        $scope.message = '';
        $scope.cmsSemester = entity;
        $scope.cmsSemester.status=true;

        $scope.cmscurriculums = FindActivecmsCurriculums.query({size: 500});
        $scope.load = function (id) {
            CmsSemester.get({id: id}, function (result) {
                $scope.cmsSemester = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cmsSemesterUpdate', result);
            $scope.isSaving = false;
            $state.go('courseInfo.cmsSemester', {}, {reload: true});

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            $state.go('courseInfo.cmsSemester', {}, {reload: true});
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cmsSemester.id != null) {
                CmsSemester.update($scope.cmsSemester, onSaveSuccess, onSaveError);
            } else {
                CmsSemester.save($scope.cmsSemester, onSaveSuccess, onSaveError);
            }
        };

        $scope.lions = false;
        $scope.cranes = false;
        $scope.dSemester = true;
        $scope.uniqueError = false;

        $scope.checkSemesterUniqueness = function () {
            console.log($scope.cmsSemester.code);
            console.log($scope.cmsSemester.name);
            if ($scope.cmsSemester.code != null && $scope.cmsSemester.name != null) {
                CmsSemesterByCodeAndName.get({code: $scope.cmsSemester.code, name: $scope.cmsSemester.name}, function (result) {
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
        };


        $scope.enableCodeAndName = function () {
            $scope.dSemester = true;
            if ($scope.cmsSemester.cmsCurriculum.duration_type == 'Semester')
                $scope.dSemester = false;
            else
                $scope.dSemester = true;
        };

        $scope.clear = function () {
            $scope.cmsSemester = null;
        };
    }]);
