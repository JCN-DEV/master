'use strict';

angular.module('stepApp')
    .controller('ResumeController',
    ['$scope', '$rootScope', '$state','EduBoard', 'EduLevel','JpSkill','JpSkillLevel', 'DistrictsByName','Sessions', 'User', 'Country', 'District','CountrysByName','JpEmployeeReference', 'JpEmployeeTraining', 'JpLanguageProficiency', 'JpLanguageProfeciencyJpEmployee', 'JpAcademicQualification', 'JpEmployeeTrainingJpEmployee', 'JpEmployeeExperienceJpEmployee', 'JpEmploymentHistoryJpEmployee', 'LangEmployee', 'ReferenceEmployee', 'Employee', 'JpEmployeeExperience', 'ExperienceEmployee', 'Reference', 'Lang', 'Training', 'SkillEmployee', 'Jobapplication', 'ProfessionalQualification', 'Principal', 'ParseLinks', 'EducationalQualificationEmployee', 'JpEmployee', 'Religion', 'AcademicQualificationJpEmployee', 'JpEmploymentHistory', 'JpEmployeeReferenceJpEmployee','$timeout','JpSkillByName','EduBoardByType','ActiveJpSkill','ActiveJpSkillLevel','ActiveEduLevels',
    function ($scope, $rootScope, $state,EduBoard, EduLevel,JpSkill,JpSkillLevel, DistrictsByName,Sessions, User, Country, District,CountrysByName,JpEmployeeReference, JpEmployeeTraining, JpLanguageProficiency, JpLanguageProfeciencyJpEmployee, JpAcademicQualification, JpEmployeeTrainingJpEmployee, JpEmployeeExperienceJpEmployee, JpEmploymentHistoryJpEmployee, LangEmployee, ReferenceEmployee, Employee, JpEmployeeExperience, ExperienceEmployee, Reference, Lang, Training, SkillEmployee, Jobapplication, ProfessionalQualification, Principal, ParseLinks, EducationalQualificationEmployee, JpEmployee, Religion, AcademicQualificationJpEmployee, JpEmploymentHistory, JpEmployeeReferenceJpEmployee,$timeout,JpSkillByName, EduBoardByType, ActiveJpSkill, ActiveJpSkillLevel, ActiveEduLevels) {

        $scope.jpEmployee = {};
        $scope.jpAcademicQualification = {};
        $scope.jpEmploymentHistory = {};
        $scope.jpEmployeeExperience = {};
        $scope.jpEmployeeTraining = {};
        $scope.jpLanguageProficiency = {};
        $scope.jpEmployeeReference = {};
        $scope.jpSkill = {};
        $scope.districts= [];
        $scope.jpAcademicQualifications= [];
        $scope.jpLanguageProficiencys= [];
        $scope.jpSkills = [];
        $scope.addNewSkill = false;
        //$scope.academicQualifications= [];


        /*$scope.eduBoards= [];
        $scope.eduLevels= [];
        $scope.jpSkills= [];
        $scope.jpSkillLevels= [];*/
        $scope.jpEmployee.nationality = "Bangladeshi";

        Principal.identity().then(function (account) {
            User.get({login: account.login}, function (result) {
                $scope.jpEmployee.user = result;
                $scope.jpEmployee.email = result.email;
                console.log(result);
                console.log($scope.jpEmployee);

            });
        });

        $scope.years = [];
        var currentYear = new Date().getFullYear();
        EduBoardByType.query({boardType :'board'}, function(result){
            $scope.eduBoards = result;
        });

        EduBoardByType.query({boardType :'university'}, function(result){
            $scope.eduUniversitys = result;
        });

        $scope.religions = Religion.query({size: 300});

        EduBoard.query({}, function(result, headers) {
            $scope.eduBoards = result;
            //console.log("laksdjflasdlf "+$scope.eduBoards);
        });

       /* EduLevel.query({}, function(result, headers) {
            $scope.eduLevels = result;
            //console.log("edu lavel "+$scope.eduLevels);
        });*/

        ActiveEduLevels.query({}, function(result, headers) {
            $scope.eduLevels = result;
            //console.log("edu lavel "+$scope.eduLevels);
        });

        ActiveJpSkill.query({page:0, size:1000}, function(result, headers) {
           // $scope.jpSkills = result;
            angular.forEach(result, function (value, key) {
                {
                    $scope.jpSkills.push({
                        description: value.description,
                        id: value.id,
                        name: value.name,
                        status: value.status
                    });
                }
                ;
            });
            //console.log("skills "+$scope.jpSkills);
        });

        ActiveJpSkillLevel.query({page:0, size:1000}, function(result, headers) {
            $scope.jpSkillLevels = result;
            //console.log("skill level "+$scope.jpSkillLevels);
        });
        /*$scope.eduBoards = EduBoard.query({size: 300});
        $scope.eduLevels = EduLevel.query({size: 300});
        $scope.jpSkills = JpSkill.query({size: 300});
        $scope.jpSkillLevels = JpSkillLevel.query({});*/

        /*console.log("eduBoards :"+$scope.eduBoards);
        console.log("eduLevels :"+$scope.eduLevels);
        console.log("jpSkills :"+$scope.jpSkills);
        console.log("jpSkillLevels :"+$scope.jpSkillLevels);
*/  $scope.initDates = function() {
            var i;
            for (i = currentYear ;i >= currentYear-50; i--) {
                $scope.years.push(i);
                //console.log( $scope.years);
            }
        };
        $scope.initDates();
        $scope.countrys = CountrysByName.query({size: 300});
        DistrictsByName.query({}, function(result, headers) {
            $scope.districts = result;
        });


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

        $scope.hasProfile = true;
        $scope.viewProfileMode = true;
        $scope.viewEducationMode = true;
        $scope.viewEmploymentMode = true;
        $scope.viewSkillsMode = true;
        $scope.viewTrainingsMode = true;
        $scope.viewLanguageMode = true;
        $scope.viewReferenceMode = true;

        $scope.jpSkills.push(
            {
                description: 'Other',
                id: -1,
                name: 'Other',
                status: true
            }
        );
        $scope.changeJpSkill = function () {
            console.log("skill :" + $scope.jpEmployeeExperience.jpSkill);
            if ($scope.jpEmployeeExperience.jpSkill.id == -1) {
                console.log("Other found");
                $scope.addNewSkill = true;
            } else {
                $scope.addNewSkill = false;
            }
        };
        $scope.changeProfileMode = function (value) {
            $scope.viewProfileMode = value;
        }



        $scope.changeEducationMode = function (value) {
            //$scope.clearForm();
            $timeout(function() {
                $rootScope.refreshRequiredFields();
            }, 100);
            if($scope.jpAcademicQualifications.length == 0){
                $scope.jpAcademicQualifications.push(
                    {
                        degreeTitle: null,
                        major: null,
                        institute: null,
                        isGpaResult: true,
                        resulttype: 'gpa',
                        result: null,
                        passingyear: null,
                        duration: null,
                        achivement: null,
                        cgpa: null,
                        certificateCopy: null,
                        certificateCopyContentType: null,
                        status: null,
                        id: null,
                        instEmployee: null
                    }
                );
                $timeout(function() {
                    $rootScope.refreshRequiredFields();
                }, 100);
            }

           /* if(value){
                $scope.jpAcademicQualification = {};
            }*/
            $scope.viewEducationMode = value;
        };

        $scope.AddMoreEducation = function(){
            $scope.jpAcademicQualifications.push(
                {
                    degreeTitle: null,
                    major: null,
                    institute: null,
                    isGpaResult: true,
                    resulttype: 'gpa',
                    result: null,
                    passingyear: null,
                    duration: null,
                    achivement: null,
                    cgpa: null,
                    certificateCopy: null,
                    certificateCopyContentType: null,
                    status: null,
                    id: null,
                    instEmployee: null
                }
            );
            // Start Add this code for showing required * in add more fields
            $timeout(function() {
                $rootScope.refreshRequiredFields();
            }, 100);
            // End Add this code for showing required * in add more fields

        };

        $scope.AddMoreLanguage = function(){
            $scope.jpLanguageProficiencys.push(
                {
                    name: null,
                    reading: 'Average',
                    writing: 'Average',
                    speaking: 'Average',
                    listening: 'Average'
                }
            );
            // Start Add this code for showing required * in add more fields
            $timeout(function() {
                $rootScope.refreshRequiredFields();
            }, 100);
            // End Add this code for showing required * in add more fields

        };

        $scope.changeEmploymentMode = function (value) {
            $scope.clearForm();
            if(value){
                $scope.jpEmploymentHistory = {};
            }
            $scope.viewEmploymentMode = value;
        }

        $scope.changeSkillsMode = function (value) {
            $scope.clearForm();
            if(value){
                $scope.jpEmployeeExperience = {};
            }
            $scope.viewSkillsMode = value;
        }

        $scope.changeTrainingsMode = function (value) {
            $scope.clearForm();
            if(value){
                $scope.jpEmployeeTraining = {};
            }
            $scope.viewTrainingsMode = value;
        }

        $scope.changeLanguageMode = function (value) {
            if($scope.jpLanguageProficiencys.length == 0){
                $scope.jpLanguageProficiencys.push(
                    {
                        name: null,
                        reading: 'Average',
                        writing: 'Average',
                        speaking: 'Average',
                        listening: 'Average'
                    }
                );
                // Start Add this code for showing required * in add more fields
                $timeout(function() {
                    $rootScope.refreshRequiredFields();
                }, 100);
            }

            $scope.clearForm();
            if(value){
                $scope.jpLanguageProficiency = {};
            }
            $scope.viewLanguageMode = value;
        }

        $scope.changeReferenceMode = function (value) {
            $scope.clearForm();
            if(value){
                $scope.jpEmployeeReference = {};
            }
            $scope.viewReferenceMode = value;
        }

        Principal.identity().then(function (account) {
            $scope.settingsAccount = account;
            $scope.user = User.get({'login': $scope.settingsAccount.login});
        });

        $scope.profile = function () {
            JpEmployee.get({id: 'my'}, function (result) {
                $scope.jpEmployee = result;
                if(result.picture){
                    var blob = b64toBlob(result.picture);
                    $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
                }


            }, function (response) {
                $scope.hasProfile = false;
            })
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:employeeUpdate', result);
            $scope.isSaving = false;

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        var onSaveProfileFinished = function (result) {
            $scope.changeProfileMode(true);
            $state.go('resume',{},{reload: true});
        };

        $scope.saveProfile = function () {
            if ($scope.jpEmployee.id != null) {
                JpEmployee.update($scope.jpEmployee, onSaveProfileFinished);
                $rootScope.setWarningMessage('stepApp.jpEmployee.updated');
            } else {
                Principal.identity().then(function (account) {
                    User.get({login: account.login}, function (result) {
                        $scope.jpEmployee.user = result;
                        JpEmployee.save($scope.jpEmployee, onSaveProfileFinished);
                        $rootScope.setSuccessMessage('stepApp.jpEmployee.created');
                    });
                });
            }
        };

        $scope.employeeUpdate = function () {
            $scope.isSaving = true;
            if ($scope.jpEmployee.id != null) {
                JpEmployee.update($scope.jpEmployee, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.jpEmployee.updated');
            } else {
                $scope.jpEmployee.user.id = $scope.user.id;
                JpEmployee.save($scope.jpEmployee, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.jpEmployee.created');
            }
        };

        $scope.fileRequired = null;
        console.log($scope.fileRequired);
        console.log("==============================");


        $scope.setPicture = function ($file, jpEmployee) {
            if ($file) {
                if(Math.round($file.size/1024 <120)){
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            jpEmployee.picture = base64Data;
                            var blob = b64toBlob(base64Data);
                            $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
                            //console.log("asdfasdfasdfasdfasdf"+$file.name) ;
                            jpEmployee.pictureName = $file.name;
                            jpEmployee.pictureType = $file.type;
                            console.log("file size >>>>>>>>>");
                            Math.round($file.size/1024) +'KB';
                            console.log(Math.round($file.size/1024));
                            if(jpEmployee.pictureName != null){
                                $scope.fileRequired = 'File found';
                            }
                        });
                    };
                }else{
                    alert("File size must be less than 120KB");
                }

            }
        };

        var onSaveEducationFinished = function (result) {
            AcademicQualificationJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
                $scope.jpAcademicQualifications = result;
            });
            $scope.changeEducationMode(true);
        };

        $scope.saveEducation = function () {
console.log('comes to save education');
console.log($scope.jpAcademicQualifications);
            angular.forEach($scope.jpAcademicQualifications, function(data){
                if (data.id != null) {
                    JpAcademicQualification.update(data, onSaveEducationFinished);
                    $rootScope.setWarningMessage('stepApp.jpAcademicQualification.updated');
                } else {
                    JpEmployee.get({id: 'my'}, function (result) {
                        data.jpEmployee = result;
                        JpAcademicQualification.save(data, onSaveEducationFinished);
                        $rootScope.setSuccessMessage('stepApp.jpAcademicQualification.created');
                    });
                }
            });



        };

        $scope.editEducation = function (id) {
            JpAcademicQualification.get({id: id}, function (result) {
                $scope.jpAcademicQualification = result;
                $scope.viewEducationMode = false;
            });
        };


        var onSaveEmploymentFinished = function (result) {
            $scope.careerInfo();
            $scope.changeEmploymentMode(true);
        };

        $scope.saveEmplymentHistory = function () {
            if ($scope.jpEmploymentHistory.id != null) {
                JpEmploymentHistory.update($scope.jpEmploymentHistory, onSaveEmploymentFinished);
                $rootScope.setWarningMessage('stepApp.jpEmploymentHistory.updated');

            } else {
                JpEmployee.get({id: 'my'}, function (result) {
                    $scope.jpEmploymentHistory.jpEmployee = result;
                    JpEmploymentHistory.save($scope.jpEmploymentHistory, onSaveEmploymentFinished);
                    $rootScope.setSuccessMessage('stepApp.jpEmploymentHistory.created');

                });

            }
        };

        $scope.editEmploymentHistory = function (id) {
            JpEmploymentHistory.get({id: id}, function (result) {
                $scope.jpEmploymentHistory = result;
                $scope.viewEmploymentMode = false;

            });
        };

        var onSaveSkillFinished = function (result) {
            $scope.experienceTab();
            $scope.changeSkillsMode(true);
        };

        var onSaveNewSkill = function (result) {
            $scope.jpEmployeeExperience.jpSkill = result;
            $scope.isSaving = true;
            if ($scope.jpEmployeeExperience.id != null) {
                JpEmployeeExperience.update($scope.jpEmployeeExperience, onSaveSkillFinished);
                $rootScope.setWarningMessage('stepApp.jpEmployeeExperience.updated');

            } else {
                JpEmployee.get({id: 'my'}, function (result) {
                    $scope.jpEmployeeExperience.jpEmployee = result;
                    JpEmployeeExperience.save($scope.jpEmployeeExperience, onSaveSkillFinished);
                    $rootScope.setSuccessMessage('stepApp.jpEmployeeExperience.created');

                });

            }
        }

        $scope.saveSkill = function () {
            JpSkillByName.get({name: $scope.jpSkill.name}, function(result) {
                alert('skill already exists');
                location.reload(true);
            }, function(response) {
                    if(response.status == 404 || response.status == 400){
                        if ($scope.jpEmployeeExperience.jpSkill.id == -1) {
                            //console.log("Other found");
                            $scope.jpSkill.id = null;
                            $scope.jpSkill.status = true;
                            // $scope.jpSkill.name = $scope.organizationCategory;
                            JpSkill.save($scope.jpSkill, onSaveNewSkill);

                        } else {
                            if ($scope.jpEmployeeExperience.id != null) {
                                JpEmployeeExperience.update($scope.jpEmployeeExperience, onSaveSkillFinished);
                                $rootScope.setWarningMessage('stepApp.jpEmployeeExperience.updated');

                            } else {
                                JpEmployee.get({id: 'my'}, function (result) {
                                    $scope.jpEmployeeExperience.jpEmployee = result;
                                    JpEmployeeExperience.save($scope.jpEmployeeExperience, onSaveSkillFinished);
                                    $rootScope.setSuccessMessage('stepApp.jpEmployeeExperience.created');

                                });

                            }
                        }
                    }
            });

        };

        $scope.editSkill = function (id) {
            JpEmployeeExperience.get({id: id}, function (result) {
                $scope.jpEmployeeExperience = result;
                $scope.viewSkillsMode = false;
            });
        };

        var onSaveTrainingFinished = function (result) {
            $scope.trainingsTab();
            $scope.changeTrainingsMode(true);
        };

        $scope.saveTraining = function () {
            if ($scope.jpEmployeeTraining.id != null) {
                JpEmployeeTraining.update($scope.jpEmployeeTraining, onSaveTrainingFinished);
                $rootScope.setWarningMessage('stepApp.jpEmployeeTraining.updated');
            } else {
                JpEmployee.get({id: 'my'}, function (result) {
                    $scope.jpEmployeeTraining.jpEmployee = result;
                    JpEmployeeTraining.save($scope.jpEmployeeTraining, onSaveTrainingFinished);
                    $rootScope.setSuccessMessage('stepApp.jpEmployeeTraining.created');
                });
            }
        };

        $scope.editTraining = function (id) {
            JpEmployeeTraining.get({id: id}, function (result) {
                $scope.jpEmployeeTraining = result;
                $scope.viewTrainingsMode = false;

            });
        };

        var onSaveLanguageFinished = function (result) {
            JpLanguageProfeciencyJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
                $scope.jpLanguageProficiencys = result;
            });
            $scope.languageTab();

            $scope.changeLanguageMode(true);
        };

        $scope.saveLanguage = function () {
            console.log("comes to save language");
            angular.forEach($scope.jpLanguageProficiencys, function(data) {
                if (data.id != null) {
                    JpLanguageProficiency.update(data, onSaveLanguageFinished);
                    $rootScope.setWarningMessage('stepApp.jpLanguageProficiency.updated');
                } else {
                    JpEmployee.get({id: 'my'}, function (result) {
                        data.jpEmployee = result;
                        JpLanguageProficiency.save(data, onSaveLanguageFinished);
                        $rootScope.setSuccessMessage('stepApp.jpLanguageProficiency.created');
                    });
                }
            });
        };

        $scope.editLanguage = function (id) {
            JpLanguageProficiency.get({id: id}, function (result) {
                $scope.jpLanguageProficiency = result;
                $scope.viewLanguageMode = false;

            });
        };
        $scope.cancel = function() {
            console.log('oooooooooo');
            $state.go('resume',{},{reload:true});
            //history.go('resume');
        };

        var onSaveReferenceFinished = function (result) {
            $scope.referenceTab();
            $scope.changeReferenceMode(true);
        };

        $scope.saveReference = function () {
            if ($scope.jpEmployeeReference.id != null) {
                JpEmployeeReference.update($scope.jpEmployeeReference, onSaveReferenceFinished);
                $rootScope.setWarningMessage('stepApp.jpEmployeeReference.updated');
            } else {
                JpEmployee.get({id: 'my'}, function (result) {
                    $scope.jpEmployeeReference.jpEmployee = result;
                    JpEmployeeReference.save($scope.jpEmployeeReference, onSaveReferenceFinished);
                    $rootScope.setSuccessMessage('stepApp.jpEmployeeReference.created');
                });
            }
        };

        $scope.editReference = function (id) {
            JpEmployeeReference.get({id: id}, function (result) {
                $scope.jpEmployeeReference = result;
                $scope.viewReferenceMode = false;

            });
        };

        $scope.qualifications = function () {
            ProfessionalQualification.query({id: $scope.jpEmployee.id}, function (result, headers) {
                $scope.professionalQualifications = result;
            });
        };

        $scope.educations = function () {
            AcademicQualificationJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
                $scope.jpAcademicQualifications = result;
            });
            $scope.jpAcademicQualifications.push(
                {
                    degreeTitle: null,
                    major: null,
                    institute: null,
                    isGpaResult: true,
                    resulttype: 'gpa',
                    result: null,
                    passingyear: null,
                    duration: null,
                    achivement: null,
                    cgpa: null,
                    certificateCopy: null,
                    certificateCopyContentType: null,
                    status: null,
                    id: null,
                    instEmployee: null
                }
            );

        };

        $scope.careerInfo = function () {
            JpEmploymentHistoryJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
                $scope.employments = result;
            });
        };

        $scope.experienceTab = function () {
            JpEmployeeExperienceJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
                $scope.skills = result;
            });
        };

        $scope.trainingsTab = function () {
            JpEmployeeTrainingJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
                $scope.trainings = result;
            });
        };

        $scope.languageTab = function () {

            JpLanguageProfeciencyJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
                $scope.jpLanguageProficiencys = result;
            });
        };

        $scope.referenceTab = function () {
            JpEmployeeReferenceJpEmployee.query({id: $scope.jpEmployee.id}, function (result, headers) {
                $scope.references = result;

            });
        };

        $scope.clearForm = function() {
            $('.form-group').removeClass('has-error');
        }

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
