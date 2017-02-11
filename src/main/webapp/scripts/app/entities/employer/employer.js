'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employer', {
                parent: 'entity',
                url: '/employers',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.employer.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/employers.html',
                        controller: 'EmployerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('job.applied', {
                parent: 'employer.job-list',
                url: '/{id}/job-application',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.job.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/job-application.html',
                        controller: 'JobApplicationController'
                    }
                },

                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        return $translate.refresh();
                    }]
                }
            })

            .state('job.shortListed', {
                parent: 'employer.job-list',
                url: '/{id}/shortListed',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.job.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/applicant-list.html',
                        controller: 'ShortListController'
                    }
                },

                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        return $translate.refresh();
                    }]
                }
            }).state('job.rejectedList', {
                parent: 'employer.job-list',
                url: '/{id}/rejectedListed',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.job.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/applicant-list.html',
                        controller: 'RejectedListController'
                    }
                },

                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        return $translate.refresh();
                    }]
                }
            })
            .state('job.viewedList', {
                parent: 'employer.job-list',
                url: '/{id}/viewedList',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.job.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/applicant-list.html',
                        controller: 'ViewedListController'
                    }
                },

                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        return $translate.refresh();
                    }]
                }
            })
            .state('job.interviewList', {
                parent: 'employer.job-list',
                url: '/{id}/interviewList',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.job.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/applicant-list.html',
                        controller: 'InterviewListController'
                    }
                },

                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        return $translate.refresh();
                    }]
                }
            })
            .state('job.finalList', {
                parent: 'employer.job-list',
                url: '/{id}/finalList',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.job.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/applicant-list.html',
                        controller: 'FinalListController'
                    }
                },

                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        return $translate.refresh();
                    }]
                }
            })
            .state('job.unviewedList', {
                parent: 'employer.job-list',
                url: '/{id}/unviewedList',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.job.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/applicant-list.html',
                        controller: 'UnviewedListController'
                    }
                },

                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employer.detail', {
                parent: 'entity',
                url: '/employer/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.employer.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/employer-detail.html',
                        controller: 'EmployerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employer');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Employer', function ($stateParams, Employer) {
                        return Employer.get({id: $stateParams.id});
                    }]
                }
            })
            .state('employer.new', {
                parent: 'employer',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employer/employer-dialog.html',
                        controller: 'EmployerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    alternativeCompanyName: null,
                                    contactPersonName: null,
                                    personDesignation: null,
                                    contactNumber: null,
                                    companyInformation: null,
                                    address: null,
                                    city: null,
                                    zipCode: null,
                                    companyWebsite: null,
                                    industryType: null,
                                    businessDescription: null,
                                    companyLogo: null,
                                    companyLogoContentType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function (result) {
                        $state.go('employer', null, {reload: true});
                    }, function () {
                        $state.go('employer');
                    })
                }]
            })
            .state('employer.profile', {
                parent: 'entity',
                url: '/employer-profile',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'global.menu.account.employer-profile'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/profile.html',
                        controller: 'EmployerProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('country');
                        $translatePartialLoader.addPart('division');
                        $translatePartialLoader.addPart('district');
                        $translatePartialLoader.addPart('upazila');
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('sessions');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employer.edit', {
                parent: 'employer',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_EMPLOYER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employer/employer-dialog.html',
                        controller: 'EmployerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Employer', function (Employer) {
                                return Employer.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('employer', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            })
            .state('employer.profile.detail-edit', {
                parent: 'employer.profile',
                url: '/detail-edit/{id}',
                data: {
                    authorities: ["ROLE_EMPLOYER"]
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employer/update-detail.html',
                        controller: 'IndustryDetailDialogController',
                        size: 'md',
                        resolve: {
                            entity: ['Employer', function (Employer) {
                                return Employer.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('employer.profile', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            })
            .state('employer.update-industry', {
                parent: 'employer',
                url: '/update-industry/{id}',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employer/update-detail.html',
                        controller: 'IndustryDetailDialogController',
                        size: 'md',
                        resolve: {
                            entity: ['Employer', function (Employer) {
                                return Employer.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('employer', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            })
            /*.state('employer.delete', {
                parent: 'employer',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_EMPLOYER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employer/employer-delete-dialog.html',
                        controller: 'EmployerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Employer', function (Employer) {
                                return Employer.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('employer', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            })*/

            .state('employer.new-job', {
                parent: 'entity',
                url: '/new-job',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'global.menu.account.new-job'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/new-job.html',
                        controller: 'NewJobController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('cat');
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Job', function($stateParams, Job) {
                        return Job.get({id : $stateParams.id});
                    }]
                }
            }).state('employer.edit-job', {
                parent: 'entity',
                url: '/{id}/edit-job',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'global.menu.account.new-job'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/new-job.html',
                        controller: 'NewJobController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('cat');
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                     entity: ['$stateParams', 'Job', function($stateParams, Job) {
                       console.log('id: '+$stateParams.id);
                        return Job.get({id : $stateParams.id});
                    }]
                }
            })
            .state('employer.change-password', {
                parent: 'entity',
                url: '/change-password',
                data: {
                    authorities: ['ROLE_EMPLOYER','ROLE_JPADMIN','ROLE_ADMIN','ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'global.menu.account.changePassword'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/change-password.html',
                        controller: 'EmployerChangePasswordController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('employer');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })
            .state('employer.job-list', {
                parent: 'entity',
                url: '/job-list',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'global.menu.account.job-list'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/job-list.html',
                        controller: 'JobListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('district');
                        $translatePartialLoader.addPart('upazila');
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })
            .state('employer.employer-registration', {
                parent: 'entity',
                url: '/employer-registration',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.employer-registration'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/employer-registration.html',
                        controller: 'EmployerRegistrationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('district');
                        $translatePartialLoader.addPart('upazila');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('register');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null, new: null};
                    }
                }
            })
            .state('employer.viewResume', {
                parent: 'entity',
                url: '/application/{id}',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.employer.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/applicant-resume.html',
                        controller: 'ApplicantResumeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('jpAcademicQualification');
                        $translatePartialLoader.addPart('instEmpEduQuali');
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
                        $translatePartialLoader.addPart('jpEmployee');
                        $translatePartialLoader.addPart('jpEmploymentHistory');
                        $translatePartialLoader.addPart('jpEmployeeExperience');
                        $translatePartialLoader.addPart('jpEmployeeTraining');
                        $translatePartialLoader.addPart('jpEmployeeReference');
                        $translatePartialLoader.addPart('resultType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Jobapplication', function ($stateParams, Jobapplication) {
                        return Jobapplication.get({id: $stateParams.id});
                    }]
                }
            }).state('employer.filterCv', {
                parent: 'entity',
                url: '/cv-filter',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.employer.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employer/search-applicant.html',
                        controller: 'SearchApplicantController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employer');
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
                        $translatePartialLoader.addPart('jpEmployee');
                        $translatePartialLoader.addPart('jpEmploymentHistory');
                        $translatePartialLoader.addPart('jpEmployeeExperience');
                        $translatePartialLoader.addPart('jpEmployeeTraining');
                        $translatePartialLoader.addPart('jpEmployeeReference');
                        $translatePartialLoader.addPart('resultType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Jobapplication', function ($stateParams, Jobapplication) {
                        return null;
                        //return Jobapplication.get({id: $stateParams.id});
                    }]
                }
            });

    });
