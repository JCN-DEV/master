'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('risNewAppForm', {
                parent: 'risNew',
                url: '/risNewAppForms',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_APPLICANT', 'ROLE_ADMIN'],
                    pageTitle: 'stepApp.risNewAppForm.home.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/risNewAppForms.html',
                        controller: 'RisNewAppFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('religions');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('risNewAppForm.detail', {
                parent: 'risNew',
                url: '/risNewAppForm/{id}',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_APPLICANT', 'ROLE_ADMIN'],
                    pageTitle: 'stepApp.risNewAppForm.detail.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/risNewAppForm-detail.html',
                        controller: 'RisNewAppFormDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('religion');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RisNewAppForm', function ($stateParams, RisNewAppForm) {
                        return RisNewAppForm.get({id: $stateParams.id});
                    }]
                }
            })
            .state('risNewAppForm.new', {
                parent: 'risNewAppForm',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_APPLICANT', 'ROLE_ADMIN']
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/risNewAppForm-dialog.html',
                        controller: 'RisNewAppFormDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('religion');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            designation: null,
                            circularNo: null,
                            applicationDate: null,
                            applicantsNameBn: null,
                            applicantsNameEn: null,
                            nationalId: null,
                            birthCertificateNo: null,
                            birthDate: null,
                            age: null,
                            fathersName: null,
                            mothersName: null,
                            holdingNameBnPresent: null,
                            villageBnPresent: null,
                            unionBnPresent: null,
                            poBnPresent: null,
                            poCodeBnPresent: null,
                            holdingNameBnPermanent: null,
                            villageBnPermanent: null,
                            unionBnPermanent: null,
                            poBnPermanent: null,
                            poCodeBnPermanent: null,
                            contactPhone: null,
                            email: null,
                            nationality: null,
                            profession: null,
                            religion: null,
                            holdingNameEnPresent: null,
                            villageEnPresent: null,
                            unionEnPresent: null,
                            poEnPresent: null,
                            poCodeEnPresent: null,
                            holdingNameEnPermanent: null,
                            villageEnPermanent: null,
                            unionEnPermanent: null,
                            poEnPermanent: null,
                            poCodeEnPermanent: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('risNewAppForm.edit', {
                parent: 'risNewAppForm',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_APPLICANT', 'ROLE_ADMIN'],
                    pageTitle: 'stepApp.risNewAppForm.detail.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/risNewAppForm-dialog.html',
                        controller: 'RisNewAppFormDialogController'
                    }
                },
                resolve: {
                    entity: ['RisNewAppForm', '$stateParams', function (RisNewAppForm, $stateParams) {
                        return RisNewAppForm.get({id: $stateParams.id});
                    }]
                }
            })
            .state('risNewAppForm.viewandapprove', {
                parent: 'risNew',
                url: '/viewandapprove',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.risNewAppForm.detail.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/risViewAndApprove.html',
                        controller: 'RisNewAppFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('religion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('risNewAppForm.appList', {
                parent: 'risNew',
                url: '/appList',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.risNewAppForm.detail.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/applicationList.html',
                        controller: 'RisNewAppFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('religion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('risNewAppForm.vivaApplicantList', {
                parent: 'risNew',
                url: '/vivaApplicantList',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.risNewAppForm.detail.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/vivaApplicantList.html',
                        controller: 'RisNewAppFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('religion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('risNewAppForm.WrittenExamSelection', {
                parent: 'risNew',
                url: '/WrittenExamSelection',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.risNewAppForm.detail.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/risWrittenExamSelection.html',
                        controller: 'RisNewAppFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('religion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('risNewAppForm.VivaSelection', {
                parent: 'risNew',
                url: '/VivaSelection',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.risNewAppForm.detail.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/risVivaSelection.html',
                        controller: 'RisNewAppFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('religion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('risNewAppForm.newjobpost', {
                parent: 'risNew',
                url: '/NewJobPost',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.risNewAppForm.detail.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/newJobPost.html',
                        controller: 'RisNewAppFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('religion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('risNewAppForm.getJobList', {
                parent: 'risNew',
                url: '/getJobList',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.risNewAppForm.detail.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/getJobList.html',
                        controller: 'RisNewAppFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('risNewVacancy');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })


            .state('risNewAppForm.AppointmentLetterIssue', {
                parent: 'risNew',
                url: '/AppointmentLetterIssue',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Appointment Letter Issue'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/risAppointmentLetterIssue.html',
                        controller: 'RisNewAppFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('religion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('risNewAppForm.newapplicantRegister', {
                parent: 'risNew',
                url: '/newapplicantRegister',
                data: {
                    authorities: ['ROLE_APPLICANT', 'ROLE_ADMIN'],
                    pageTitle: 'Applicant Registration'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/risNewapplicantRegister.html',
                        controller: 'RisRegistrationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('religion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('risNewAppForm.circularDash', {
                parent: 'risNew',
                url: '/circulardash',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Applicant Registration'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/circularDash.html',
                        controller: 'RisNewAppFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('religion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('risNewAppForm.AdminViewAndPost', {
                parent: 'risNew',
                url: '/risAdminViewAndPost',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Approved Jobs View and Post '
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/risAdminViewAndPost.html',
                        controller: 'RisNewAppFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('risNewVacancy');
                        $translatePartialLoader.addPart('religion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('risNewAppForm.Ministry', {
                parent: 'risNew',
                url: '/Ministry',
                data: {
                    authorities: ['ROLE_MINISTER'],
                    pageTitle: 'Ministry Job Approval '
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewAppForm/risMinistryAprove.html',
                        controller: 'RisNewAppFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewAppForm');
                        $translatePartialLoader.addPart('religion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
    });
