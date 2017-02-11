'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('nameCnclApplication', {
                parent: 'entity',
                url: '/nameCnclApplications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.nameCnclApplication.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/nameCnclApplication/nameCnclApplications.html',
                        controller: 'NameCnclApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('nameCnclApplication');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('nameCnclApplication.detail', {
                parent: 'entity',
                url: '/nameCnclApplication/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.nameCnclApplication.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/nameCnclApplication/nameCnclApplication-detail.html',
                        controller: 'NameCnclApplicationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('nameCnclApplication');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'NameCnclApplication', function($stateParams, NameCnclApplication) {
                        return NameCnclApplication.get({id : $stateParams.id});
                    }]
                }
            }).state('nameCnclApplicationStatus', {
                parent: 'mpo',
                url: '/name-cancellation-Application-status/{code}',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/nameCancellation/nameCnclApplication/name-cancellation-application-status.html',
                        controller: 'NameCnclStatusLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })
            .state('nameCnclApplication.new', {
                parent: 'nameCnclApplication',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/nameCnclApplication/nameCnclApplication-dialog.html',
                        controller: 'NameCnclApplicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    created_date: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('nameCnclApplication', null, { reload: true });
                    }, function() {
                        $state.go('nameCnclApplication');
                    })
                }]
            })
            .state('nameCnclApplication.edit', {
                parent: 'nameCnclApplication',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/nameCnclApplication/nameCnclApplication-dialog.html',
                        controller: 'NameCnclApplicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['NameCnclApplication', function(NameCnclApplication) {
                                return NameCnclApplication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('nameCnclApplication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('nameCnclApplication.delete', {
                parent: 'nameCnclApplication',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/nameCnclApplication/nameCnclApplication-delete-dialog.html',
                        controller: 'NameCnclApplicationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['NameCnclApplication', function(NameCnclApplication) {
                                return NameCnclApplication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('nameCnclApplication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
