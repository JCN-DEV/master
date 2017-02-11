'use strict';

angular.module('stepApp')
    .controller('ViewResumeController',
    ['$scope','JpEmployeeReferenceJpEmployee', 'JpLanguageProfeciencyJpEmployee','AcademicQualificationJpEmployee','JpEmployeeTrainingJpEmployee','JpEmployeeExperienceJpEmployee', 'JpEmploymentHistoryJpEmployee', 'Sessions', 'User', 'LangEmployee', 'Employee', 'ReferenceEmployee', 'Jobapplication', 'EducationalQualificationEmployee', 'ProfessionalQualification', 'ExperienceEmployee', 'SkillEmployee', 'Principal', 'ParseLinks','JpEmployee',
    function ($scope,JpEmployeeReferenceJpEmployee, JpLanguageProfeciencyJpEmployee,AcademicQualificationJpEmployee,JpEmployeeTrainingJpEmployee,JpEmployeeExperienceJpEmployee, JpEmploymentHistoryJpEmployee, Sessions, User, LangEmployee, Employee, ReferenceEmployee, Jobapplication, EducationalQualificationEmployee, ProfessionalQualification, ExperienceEmployee, SkillEmployee, Principal, ParseLinks,JpEmployee) {
        Principal.identity().then(function (account) {
            $scope.account = account;
        });
        $scope.loadAll = function() {
            JpEmployee.get({id: 'my'}, function (result) {
                $scope.jpEmployee = result;

                    var blob = b64toBlob($scope.jpEmployee.picture);
                    $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
                    //console.log(applicaiton);

               /* ProfessionalQualification.query({id: $scope.jpEmployee.id}, function (result, headers) {
                    $scope.professionalQualifications = result;
                });*/

                AcademicQualificationJpEmployee.query({id: $scope.jpEmployee.id},function (result, headers){
                    $scope.academicQualifications = result;

                });

                JpEmploymentHistoryJpEmployee.query({id: $scope.jpEmployee.id},function (result, headers){
                    $scope.employments = result;
                });

                JpEmployeeExperienceJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
                    $scope.skills = result;

                });

                JpEmployeeTrainingJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
                    $scope.trainings = result;

                });

                JpLanguageProfeciencyJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
                    $scope.langs = result;
                });
                JpEmployeeReferenceJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
                    $scope.references = result;

                });
            })

            /*Employee.get({id: 'my'}, function (result) {
                $scope.employee = result;

                ProfessionalQualification.query({id: $scope.employee.id}, function (result, headers) {
                    $scope.professionalQualifications = result;

                });

                EducationalQualificationEmployee.query({id: $scope.employee.id}, function (result, headers) {
                    $scope.educationalQualifications = result;

                });

                SkillEmployee.query({id: $scope.employee.id}, function (result, headers) {
                    $scope.skills = result;

                });

                ExperienceEmployee.query({id: $scope.employee.id}, function (result, headers) {
                    $scope.experiences = result;

                });

                LangEmployee.query({id: $scope.employee.id}, function (result, headers) {
                    $scope.langs = result;
                });

                ReferenceEmployee.query({id: $scope.employee.id}, function (result, headers) {
                    $scope.references = result;

                });

            })*/;

        };



        function b64toBlob(b64Data, contentType, sliceSize) {
            contentType = contentType || '';
            sliceSize = sliceSize || 512;

            var byteCharacters = atob(b64Data);
            var byteArrays = [];

            for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
                var slice = byteCharacters.slice(offset, offset + sliceSize);

                var byteNumbers = new Array(slice.length);
                for (var i = 0; i < slice.length; i++) {
                    byteNumbers[i] = slice.charCodeAt(i);
                }

                var byteArray = new Uint8Array(byteNumbers);

                byteArrays.push(byteArray);
            }

            var blob = new Blob(byteArrays, {type: contentType});
            return blob;
        }
        $scope.loadAll();



    }]);
