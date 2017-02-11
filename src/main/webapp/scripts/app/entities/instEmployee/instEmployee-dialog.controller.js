'use strict';

angular.module('stepApp').controller('InstEmployeeDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'DataUtils', 'entity', 'InstEmployee', 'Institute', 'Designation', 'Religion', 'Quota', 'CourseTech', 'GradeSetup', 'InstEmpAddress', 'InstEmpEduQuali', 'InstEmplHist',
        function($scope, $stateParams, $modalInstance, $q, DataUtils, entity, InstEmployee, Institute, Designation, Religion, Quota, CourseTech, GradeSetup, InstEmpAddress, InstEmpEduQuali, InstEmplHist) {

        $scope.instEmployee = entity;
        $scope.institutes = Institute.query();
        $scope.designations = Designation.query({filter: 'instemployee-is-null'});
        $q.all([$scope.instEmployee.$promise, $scope.designations.$promise]).then(function() {
            if (!$scope.instEmployee.designation.id) {
                return $q.reject();
            }
            return Designation.get({id : $scope.instEmployee.designation.id}).$promise;
        }).then(function(designation) {
            $scope.designations.push(designation);
        });
        $scope.religions = Religion.query({filter: 'instemployee-is-null'});
        $q.all([$scope.instEmployee.$promise, $scope.religions.$promise]).then(function() {
            if (!$scope.instEmployee.religion.id) {
                return $q.reject();
            }
            return Religion.get({id : $scope.instEmployee.religion.id}).$promise;
        }).then(function(religion) {
            $scope.religions.push(religion);
        });
        $scope.quotas = Quota.query({filter: 'instemployee-is-null'});
        $q.all([$scope.instEmployee.$promise, $scope.quotas.$promise]).then(function() {
            if (!$scope.instEmployee.quota.id) {
                return $q.reject();
            }
            return Quota.get({id : $scope.instEmployee.quota.id}).$promise;
        }).then(function(quota) {
            $scope.quotas.push(quota);
        });
        $scope.coursetechs = CourseTech.query({filter: 'instemployee-is-null'});
        $q.all([$scope.instEmployee.$promise, $scope.coursetechs.$promise]).then(function() {
            if (!$scope.instEmployee.courseTech.id) {
                return $q.reject();
            }
            return CourseTech.get({id : $scope.instEmployee.courseTech.id}).$promise;
        }).then(function(courseTech) {
            $scope.coursetechs.push(courseTech);
        });
        $scope.gradesetups = GradeSetup.query({filter: 'instemployee-is-null'});
        $q.all([$scope.instEmployee.$promise, $scope.gradesetups.$promise]).then(function() {
            if (!$scope.instEmployee.gradeSetup.id) {
                return $q.reject();
            }
            return GradeSetup.get({id : $scope.instEmployee.gradeSetup.id}).$promise;
        }).then(function(gradeSetup) {
            $scope.gradesetups.push(gradeSetup);
        });
        $scope.instempaddresss = InstEmpAddress.query();
        $scope.instempeduqualis = InstEmpEduQuali.query();
        $scope.instemplhists = InstEmplHist.query();
        $scope.load = function(id) {
            InstEmployee.get({id : id}, function(result) {
                $scope.instEmployee = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmployeeUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmployee.id != null) {
                InstEmployee.update($scope.instEmployee, onSaveSuccess, onSaveError);
            } else {
                InstEmployee.save($scope.instEmployee, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setImage = function ($file, instEmployee) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmployee.image = base64Data;
                        instEmployee.imageContentType = $file.type;
                    });
                };
            }
        };

        $scope.setNidImage = function ($file, instEmployee) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmployee.nidImage = base64Data;
                        instEmployee.nidImageContentType = $file.type;
                    });
                };
            }
        };

        $scope.setBirthCertImage = function ($file, instEmployee) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmployee.birthCertImage = base64Data;
                        instEmployee.birthCertImageContentType = $file.type;
                    });
                };
            }
        };
}]);
