'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jobType', {
                parent: 'entity',
                url: '/jobTypes',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.jobType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobType/jobTypes.html',
                        controller: 'JobTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jobType.detail', {
                parent: 'entity',
                url: '/jobType/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.jobType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobType/jobType-detail.html',
                        controller: 'JobTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JobType', function($stateParams, JobType) {
                        return JobType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jobType.new', {
                parent: 'jobType',
                url: '/new',
                /*data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jobType/jobType-dialog.html',
                        controller: 'JobTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('jobType', null, { reload: true });
                    }, function() {
                        $state.go('jobType');
                    })
                }]
            })*/
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.jobType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobType/jobType-dialog.html',
                        controller: 'JobTypeDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['JobType', function(JobType) {
                        return null;
                    }]
                }
            })
            .state('jobType.edit', {
                parent: 'jobType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.jobType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobType/jobType-dialog.html',
                        controller: 'JobTypeDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JobType', function($stateParams, JobType) {
                        return JobType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jobType.delete', {
                parent: 'jobType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jobType/jobType-delete-dialog.html',
                        controller: 'JobTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['JobType', function(JobType) {
                                return JobType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('jobType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
