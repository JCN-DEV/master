'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jpEmployeeReference', {
                parent: 'entity',
                url: '/jpEmployeeReferences',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployeeReference.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployeeReference/jpEmployeeReferences.html',
                        controller: 'JpEmployeeReferenceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployeeReference');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jpEmployeeReference.detail', {
                parent: 'entity',
                url: '/jpEmployeeReference/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployeeReference.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployeeReference/jpEmployeeReference-detail.html',
                        controller: 'JpEmployeeReferenceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployeeReference');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmployeeReference', function($stateParams, JpEmployeeReference) {
                        return JpEmployeeReference.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpEmployeeReference.new', {
                parent: 'jpEmployeeReference',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployeeReference.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployeeReference/jpEmployeeReference-form.html',
                        controller: 'JpEmployeeReferenceFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployeeReference');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmployeeReference', function($stateParams, JpEmployeeReference) {
                        //return JpEmployeeReference.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpEmployeeReference.edit', {
                parent: 'jpEmployeeReference',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpEmployeeReference.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpEmployeeReference/jpEmployeeReference-form.html',
                        controller: 'JpEmployeeReferenceFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpEmployeeReference');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpEmployeeReference', function($stateParams, JpEmployeeReference) {
                        return JpEmployeeReference.get({id : $stateParams.id});
                    }]
                }
            });
    });
