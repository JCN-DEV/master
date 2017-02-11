'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('risNewVacancy', {
                parent: 'risNew',
                url: '/risNewVacancys',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.risNewVacancy.home.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewVacancy/risNewVacancys.html',
                        controller: 'RisNewVacancyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewVacancy');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('risNewVacancy.detail', {
                parent: 'risNew',
                url: '/risNewVacancy/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.risNewVacancy.detail.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewVacancy/risNewVacancy-detail.html',
                        controller: 'RisNewVacancyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewVacancy');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RisNewVacancy', function($stateParams, RisNewVacancy) {
                        return RisNewVacancy.get({id : $stateParams.id});
                    }]
                }
            })
            .state('risNewVacancy.new', {
                parent: 'risNewVacancy',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewVacancy/risNewVacancy-dialog.html',
                        controller: 'RisNewVacancyDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewVacancy');
                        return $translate.refresh();
                    }],
                     entity: function () {
                        return {
                            vacancyNo: null,
                            educationalQualification: null,
                            otherQualification: null,
                            remarks: null,
                            publishDate: null,
                            applicationDate: null,
                            attachment: null,
                            attachmentContentType: null,
                            createdDate: null,
                            updatedDate: null,
                            createdBy: null,
                            updatedBy: null,
                            status: null,
                            id: null
                        };
                    }
                }
            })
            .state('risNewVacancy.edit', {
                parent: 'risNewVacancy',
                url: '/risNewVacancy/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.risNewVacancy.detail.title'
                },
                views: {
                    'risManagementView@risNew': {
                        templateUrl: 'scripts/app/entities/risNew/risNewVacancy/risNewVacancy-dialog.html',
                        controller: 'RisNewVacancyDialogController'
                    }
                },
                resolve: {
                    entity: ['RisNewVacancy','$stateParams', function(RisNewVacancy,$stateParams) {
                        return RisNewVacancy.get({id : $stateParams.id});
                    }]
                }
            });
    });
