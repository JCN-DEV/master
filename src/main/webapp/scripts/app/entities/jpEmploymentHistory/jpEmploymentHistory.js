'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jpEmploymentHistory', {
                parent: 'entity',
                url: '/jpEmploymentHistorys',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmploymentHistory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmploymentHistory/jpEmploymentHistorys.html',
                        controller: 'JpEmploymentHistoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmploymentHistory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jpEmploymentHistory.detail', {
                parent: 'entity',
                url: '/jpEmploymentHistory/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmploymentHistory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmploymentHistory/jpEmploymentHistory-detail.html',
                        controller: 'JpEmploymentHistoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmploymentHistory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmploymentHistory', function($stateParams, JpEmploymentHistory) {
                        return JpEmploymentHistory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpEmploymentHistory.new', {
                parent: 'jpEmploymentHistory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmploymentHistory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmploymentHistory/jpEmploymentHistory-form.html',
                        controller: 'JpEmploymentHistoryFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmploymentHistory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmploymentHistory', function($stateParams, JpEmploymentHistory) {

                    }]
                }
            })
            .state('jpEmploymentHistory.edit', {
                parent: 'jpEmploymentHistory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmploymentHistory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmploymentHistory/jpEmploymentHistory-form.html',
                        controller: 'JpEmploymentHistoryFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmploymentHistory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmploymentHistory', function($stateParams, JpEmploymentHistory) {
                        return JpEmploymentHistory.get({id : $stateParams.id});
                    }]
                }
            });
    });
