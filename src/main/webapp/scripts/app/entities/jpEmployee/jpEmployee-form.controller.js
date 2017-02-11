'use strict';

angular.module('stepApp').controller('JpEmployeeFormController',
    ['$scope', '$state', '$stateParams', '$q', 'entity', 'JpEmployee', 'Principal', 'User', 'Religion', 'JpAcademicQualification', 'JpEmployeeExperience', 'JpEmploymentHistory', 'JpEmployeeReference', 'JpLanguageProficiency', 'JpEmployeeTraining',
    function ($scope, $state, $stateParams, $q, entity, JpEmployee, Principal, User, Religion, JpAcademicQualification, JpEmployeeExperience, JpEmploymentHistory, JpEmployeeReference, JpLanguageProficiency, JpEmployeeTraining) {

        $scope.jpEmployee = entity;

        //console.log($scope.jpEmployee);

        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        $scope.users = User.query();
        $scope.religions = Religion.query();
        $scope.jpacademicqualifications = JpAcademicQualification.query();
        $scope.jpemployeeexperiences = JpEmployeeExperience.query();
        $scope.jpemploymenthistorys = JpEmploymentHistory.query();
        $scope.jpemployeereferences = JpEmployeeReference.query();
        $scope.jplanguageproficiencys = JpLanguageProficiency.query();
        //$scope.jpemployeetrainings = JpEmployeeTraining.query();
        /*$scope.load = function(id) {
         JpEmployee.get({id : id}, function(result) {
         $scope.jpEmployee = result;
         });
         };
         */
        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:jpEmployeeUpdate', result);
            //$modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.jpEmployee.id != null) {
                JpEmployee.update($scope.jpEmployee, onSaveFinished);
                $state.go('resume');
            } else {
                Principal.identity().then(function (account) {
                    User.get({login: account.login}, function (result) {
                        $scope.jpEmployee.user = result;
                        JpEmployee.save($scope.jpEmployee, onSaveFinished);
                        $state.go('resume');
                    });
                });
            }
        };

        $scope.clear = function () {
            //$modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = function (text) {
            if (!angular.isString(text)) {
                return '';
            }
            if (text.length < 30) {
                return text;
            }
            return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
        };

        $scope.byteSize = function (base64String) {
            if (!angular.isString(base64String)) {
                return '';
            }
            function endsWith(suffix, str) {
                return str.indexOf(suffix, str.length - suffix.length) !== -1;
            }

            function paddingSize(base64String) {
                if (endsWith('==', base64String)) {
                    return 2;
                }
                if (endsWith('=', base64String)) {
                    return 1;
                }
                return 0;
            }

            function size(base64String) {
                return base64String.length / 4 * 3 - paddingSize(base64String);
            }

            function formatAsBytes(size) {
                return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
            }

            return formatAsBytes(size(base64String));
        };

        $scope.setCv = function ($file, jpEmployee) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function () {
                        jpEmployee.cv = base64Data;
                        jpEmployee.cvContentType = $file.type;
                        jpEmployee.cvName = $file.name;
                    });
                };
            }
        };


    }]);
