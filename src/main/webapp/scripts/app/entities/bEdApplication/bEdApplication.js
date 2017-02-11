'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bEdApplication', {
                parent: 'entity',
                url: '/bEdApplications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.bEdApplication.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bEdApplication/bEdApplications.html',
                        controller: 'BEdApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bEdApplication');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bEdApplication.detail', {
                parent: 'entity',
                url: '/bEdApplication/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.bEdApplication.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bEdApplication/bEdApplication-detail.html',
                        controller: 'BEdApplicationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bEdApplication');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'BEdApplication', function($stateParams, BEdApplication) {
                        return BEdApplication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bEdApplication.new', {
                parent: 'bEdApplication',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bEdApplication/bEdApplication-dialog.html',
                        controller: 'BEdApplicationDialogController',
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
                        $state.go('bEdApplication', null, { reload: true });
                    }, function() {
                        $state.go('bEdApplication');
                    })
                }]
            })
            .state('bEdApplication.edit', {
                parent: 'bEdApplication',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bEdApplication/bEdApplication-dialog.html',
                        controller: 'BEdApplicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['BEdApplication', function(BEdApplication) {
                                return BEdApplication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bEdApplication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('bEdApplication.delete', {
                parent: 'bEdApplication',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bEdApplication/bEdApplication-delete-dialog.html',
                        controller: 'BEdApplicationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['BEdApplication', function(BEdApplication) {
                                return BEdApplication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bEdApplication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
