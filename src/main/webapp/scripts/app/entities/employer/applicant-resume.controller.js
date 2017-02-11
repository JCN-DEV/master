'use strict';

angular.module('stepApp')
    .controller('ApplicantResumeController',
    ['$scope', '$stateParams', '$rootScope', 'entity', 'JpEmployeeReferenceJpEmployee', 'JpLanguageProfeciencyJpEmployee', 'AcademicQualificationJpEmployee', 'JpEmployeeTrainingJpEmployee', 'JpEmployeeExperienceJpEmployee', 'JpEmploymentHistoryJpEmployee', 'Sessions', 'User', 'LangEmployee', 'Employee', 'ReferenceEmployee', 'Jobapplication',
     function ($scope, $stateParams, $rootScope, entity, JpEmployeeReferenceJpEmployee, JpLanguageProfeciencyJpEmployee, AcademicQualificationJpEmployee, JpEmployeeTrainingJpEmployee, JpEmployeeExperienceJpEmployee, JpEmploymentHistoryJpEmployee, Sessions, User, LangEmployee, Employee, ReferenceEmployee, Jobapplication) {
        //$scope.jobApplication = entity;

        $scope.showInternal = true;
        /*$scope.content = '';*/
        $scope.jobApplication = {};

        Jobapplication.get({id: $stateParams.id}, function(response){
            $scope.jobApplication = response;
            response.cvViewed = true;

            if (response.id != null) {
                Jobapplication.update(response);
            }

            if ($scope.jobApplication.cvType === 'external') {
                $scope.showInternal = false;
            }

            $scope.jpEmployee = $scope.jobApplication.jpEmployee;

            var blob = $rootScope.b64toBlob($scope.jpEmployee.cv, 'application/pdf');
            $scope.content = (window.URL || window.webkitURL).createObjectURL(blob);

            var blob2 = b64toBlob($scope.jpEmployee.picture);
            $scope.url = (window.URL || window.webkitURL).createObjectURL( blob2 );

            AcademicQualificationJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
                $scope.academicQualifications = result;
            });

            JpEmploymentHistoryJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
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
        });


        $scope.click = function(){
            window.open($scope.content);
        };

        $scope.print = function(){
            window.print();
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


    }]);
