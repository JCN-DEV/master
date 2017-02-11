'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('quota', {
                parent: 'entity',
                url: '/quotas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.quota.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/quota/quotas.html',
                        controller: 'QuotaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('quota');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('quota.detail', {
                parent: 'entity',
                url: '/quota/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.quota.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/quota/quota-detail.html',
                        controller: 'QuotaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('quota');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Quota', function($stateParams, Quota) {
                        return Quota.get({id : $stateParams.id});
                    }]
                }
            })
            .state('quota.new', {
                parent: 'quota',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/quota/quota-dialog.html',
                        controller: 'QuotaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    certificate: null,
                                    certificateContentType: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('quota', null, { reload: true });
                    }, function() {
                        $state.go('quota');
                    })
                }]
            })
            .state('quota.edit', {
                parent: 'quota',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/quota/quota-dialog.html',
                        controller: 'QuotaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Quota', function(Quota) {
                                return Quota.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('quota', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('quota.delete', {
                parent: 'quota',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/quota/quota-delete-dialog.html',
                        controller: 'QuotaDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Quota', function(Quota) {
                                return Quota.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('quota', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
