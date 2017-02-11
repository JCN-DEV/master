'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jpLanguageProficiency', {
                parent: 'entity',
                url: '/jpLanguageProficiencys',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpLanguageProficiency.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpLanguageProficiency/jpLanguageProficiencys.html',
                        controller: 'JpLanguageProficiencyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpLanguageProficiency');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jpLanguageProficiency.detail', {
                parent: 'entity',
                url: '/jpLanguageProficiency/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpLanguageProficiency.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpLanguageProficiency/jpLanguageProficiency-detail.html',
                        controller: 'JpLanguageProficiencyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpLanguageProficiency');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpLanguageProficiency', function($stateParams, JpLanguageProficiency) {
                        return JpLanguageProficiency.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpLanguageProficiency.new', {
                parent: 'jpLanguageProficiency',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpLanguageProficiency.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpLanguageProficiency/jpLanguageProficiency-form.html',
                        controller: 'JpLanguageProficiencyFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpLanguageProficiency');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpLanguageProficiency', function($stateParams, JpLanguageProficiency) {
                        //return JpLanguageProficiency.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpLanguageProficiency.edit', {
                parent: 'jpLanguageProficiency',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpLanguageProficiency.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpLanguageProficiency/jpLanguageProficiency-form.html',
                        controller: 'JpLanguageProficiencyFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpLanguageProficiency');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpLanguageProficiency', function($stateParams, JpLanguageProficiency) {
                        return JpLanguageProficiency.get({id : $stateParams.id});
                    }]
                }
            });
    });
