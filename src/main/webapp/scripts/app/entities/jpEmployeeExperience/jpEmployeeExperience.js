'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jpEmployeeExperience', {
                parent: 'entity',
                url: '/jpEmployeeExperiences',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployeeExperience.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployeeExperience/jpEmployeeExperiences.html',
                        controller: 'JpEmployeeExperienceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployeeExperience');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jpEmployeeExperience.detail', {
                parent: 'entity',
                url: '/jpEmployeeExperience/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployeeExperience.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployeeExperience/jpEmployeeExperience-detail.html',
                        controller: 'JpEmployeeExperienceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployeeExperience');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmployeeExperience', function($stateParams, JpEmployeeExperience) {
                        return JpEmployeeExperience.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpEmployeeExperience.new', {
                parent: 'jpEmployeeExperience',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployeeExperience.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployeeExperience/jpEmployeeExperience-form.html',
                        controller: 'JpEmployeeExperienceFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployeeExperience');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmployeeExperience', function($stateParams, JpEmployeeExperience) {

                    }]
                }
            })
            .state('jpEmployeeExperience.edit', {
                parent: 'jpEmployeeExperience',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployeeExperience.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployeeExperience/jpEmployeeExperience-form.html',
                        controller: 'JpEmployeeExperienceFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployeeExperience');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmployeeExperience', function($stateParams, JpEmployeeExperience) {
                        return JpEmployeeExperience.get({id : $stateParams.id});
                    }]
                }
            });
    });
