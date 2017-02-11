'use strict';

angular.module('stepApp').controller('CmsSemesterDialogController',
['$scope', '$stateParams', '$state', 'entity', 'CmsSemester', 'CmsCurriculum', 'CmsSemesterByCodeAndName',
    function ($scope, $stateParams, $state, entity, CmsSemester, CmsCurriculum, CmsSemesterByCodeAndName) {

        $scope.message = '';
        $scope.cmsSemester = entity;
                $scope.cmsSemester.status=true;

        $scope.cmscurriculums = CmsCurriculum.query();
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
                // CmsSemester.save($scope.cmsSemester, onSaveSuccess, onSaveError);
                CmsSemesterByCodeAndName.get({
                        code: $scope.cmsSemester.code,
                        name: $scope.cmsSemester.name
                    }, function (cmsSemester) {
                        console.log('exist');
                        $scope.message = "The combination of Code and Name should be unique.";
                    },
                    function (response) {
                        if (response.status === 404) {
                            console.log('not exist');
                            CmsSemester.save($scope.cmsSemester, onSaveSuccess, onSaveError);
                            console.log($scope.cmsSemester);
                        }
                    }
                );
            }
        };

        $scope.lions = false;
        $scope.cranes = false;
        $scope.isDiploma = true;
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
            $scope.isDiploma = true;
            if ($scope.cmsSemester.cmsCurriculum.code.toLowerCase() == 'diploma01')
                $scope.isDiploma = false;
            else
                $scope.isDiploma = true;
        };

        $scope.clear = function () {
            $scope.cmsSemester = null;
        };
    }]);
