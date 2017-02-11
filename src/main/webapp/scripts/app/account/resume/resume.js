'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('resume', {
                parent: 'account',
                url: '/resume',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'global.menu.account.resume'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/resume/resume.html',
                        controller: 'ResumeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('educationalQualification');
                        $translatePartialLoader.addPart('professionalQualification');
                        $translatePartialLoader.addPart('instEmpEduQuali');
                        $translatePartialLoader.addPart('experience');
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('resume');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('jpEmployee');
                        $translatePartialLoader.addPart('skill');
                        $translatePartialLoader.addPart('training');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('lang');
                        $translatePartialLoader.addPart('jpSkill');
                        $translatePartialLoader.addPart('reference');
                        $translatePartialLoader.addPart('jpEmploymentHistory');
                        $translatePartialLoader.addPart('jpEmployeeExperience');
                        $translatePartialLoader.addPart('jpEmployeeTraining');
                        $translatePartialLoader.addPart('jpEmployeeReference');
                        $translatePartialLoader.addPart('employeeGender');
                        $translatePartialLoader.addPart('maritialStatus');
                        $translatePartialLoader.addPart('nationality');
                        $translatePartialLoader.addPart('employeeAvailability');
                        $translatePartialLoader.addPart('jpAcademicQualification');
                        $translatePartialLoader.addPart('jpLanguageProficiency');
                        $translatePartialLoader.addPart('resultType');
                        return $translate.refresh();

                    }]
                }
            })
            .state('resume.view', {
                parent: 'account',
                url: '/view-resume',
                data: {
                   // authorities: ['ROLE_USER'],
                    pageTitle: 'global.menu.account.viewResume'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/resume/view-resume.html',
                        controller: 'ViewResumeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('educationalQualification');
                        $translatePartialLoader.addPart('professionalQualification');
                        $translatePartialLoader.addPart('experience');
                        $translatePartialLoader.addPart('jpAcademicQualification');
                        $translatePartialLoader.addPart('instEmpEduQuali');
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('resume');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('skill');
                        $translatePartialLoader.addPart('training');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('lang');
                        $translatePartialLoader.addPart('reference');
                        $translatePartialLoader.addPart('jpEmployee');
                        $translatePartialLoader.addPart('jpEmploymentHistory');
                        $translatePartialLoader.addPart('jpEmployeeExperience');
                        $translatePartialLoader.addPart('jpEmployeeTraining');
                        $translatePartialLoader.addPart('jpEmployeeReference');
                        return $translate.refresh();
                    }]
                }
            }).state('resume.cvAttachment', {
                parent: 'account',
                url: '/resume-attachment',
                data: {
                   // authorities: ['ROLE_USER'],
                    pageTitle: 'global.menu.account.viewResume'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/resume/attachment.html',
                        controller: 'ResumeAttachmentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('educationalQualification');
                        $translatePartialLoader.addPart('professionalQualification');
                        $translatePartialLoader.addPart('experience');
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('resume');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('skill');
                        $translatePartialLoader.addPart('training');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('lang');
                        $translatePartialLoader.addPart('reference');
                        return $translate.refresh();
                    }]
                }
            })
        .state('resume.profile', {
            parent: 'account',
            url: '/profile',
            data: {
                // authorities: ['ROLE_USER'],
                pageTitle: 'global.menu.account.viewResume'
            },
            views: {
                'content@': {
                    templateUrl: 'scripts/app/account/resume/profile.html',
                    controller: 'ProfileController'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('educationalQualification');
                    $translatePartialLoader.addPart('professionalQualification');
                    $translatePartialLoader.addPart('instEmpEduQuali');
                    $translatePartialLoader.addPart('experience');
                    $translatePartialLoader.addPart('settings');
                    $translatePartialLoader.addPart('resume');
                    $translatePartialLoader.addPart('employee');
                    $translatePartialLoader.addPart('skill');
                    $translatePartialLoader.addPart('training');
                    $translatePartialLoader.addPart('gender');
                    $translatePartialLoader.addPart('lang');
                    $translatePartialLoader.addPart('reference');
                    return $translate.refresh();
                }]
            }
        })
            .state('educationDelete', {
                parent: 'resume',
                url: '/{id}/deleteEducation',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {

                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpAcademicQualification/jpAcademicQualification-delete-dialog.html',
                        controller: 'JpAcademicQualificationrDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['JpAcademicQualification', function (JpAcademicQualification) {
                                return JpAcademicQualification.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('resume', null, {reload: true});
                        }, function () {
                            $state.go('resume', null, {reload: true});
                        })
                }]
            })
            .state('referenceDelete', {
                parent: 'resume',
                url: '/{id}/deleteReference',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {

                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpEmployeeReference/jpEmployeeReference-delete-dialog.html',
                        controller: 'JpEmployeeReferenceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['JpEmployeeReference', function (JpEmployeeReference) {
                                return JpEmployeeReference.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('resume', null, {reload: true});
                        }, function () {
                            $state.go('resume', null, {reload: true});
                        })
                }]
            })
            .state('experienceDelete', {
                parent: 'resume',
                url: '/{id}/deleteExperience',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {

                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpEmployeeExperience/jpEmployeeExperience-delete-dialog.html',
                        controller: 'JpEmployeeExperienceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['JpEmployeeExperience', function (JpEmployeeExperience) {
                                return JpEmployeeExperience.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('resume', null, {reload: true});
                        }, function () {
                            $state.go('resume', null, {reload: true});
                        })
                }]
            })
            .state('trainingDelete', {
                parent: 'resume',
                url: '/{id}/deleteTraining',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {

                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpEmployeeTraining/jpEmployeeTraining-delete-dialog.html',
                        controller: 'JpEmployeeTrainingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['JpEmployeeTraining', function (JpEmployeeTraining) {
                                return JpEmployeeTraining.get({id: $stateParams.id});
                            }]
                        }

                    }).result.then(function (result) {
                            $state.go('resume', null, {reload: true});
                        }, function () {
                            $state.go('resume', null, {reload: true});
                        })
                }]
            })
            .state('employmentHistoryDelete', {
                parent: 'resume',
                url: '/{id}/deleteHistory',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {

                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpEmploymentHistory/jpEmploymentHistory-delete-dialog.html',
                        controller: 'JpEmploymentHIstoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['JpEmploymentHistory', function (JpEmploymentHistory) {
                                return JpEmploymentHistory.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('resume', null, {reload: true});
                        }, function () {
                            $state.go('resume', null, {reload: true});
                        })
                }]
            })
            .state('languageDelete', {
                parent: 'resume',
                url: '/{id}/deleteLanguage',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {

                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpLanguageProficiency/jpLanguageProficiency-delete-dialog.html',
                        controller: 'JpLanguageProficiencyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['JpLanguageProficiency', function (JpLanguageProficiency) {
                                return JpLanguageProficiency.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('resume', null, {reload: true});
                        }, function () {
                            $state.go('resume', null, {reload: true});
                        })
                }]
            });


    });
