'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instGovernBody', {
                parent: 'entity',
                url: '/instGovernBodys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instGovernBody.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instGovernBody/instGovernBodys.html',
                        controller: 'InstGovernBodyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGovernBody');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instGovernBody.detail', {
                parent: 'entity',
                url: '/instGovernBody/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instGovernBody.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instGovernBody/instGovernBody-detail.html',
                        controller: 'InstGovernBodyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialnLoader.addPart('instGovernBody');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstGovernBody', function($stateParams, InstGovernBody) {
                        return InstGovernBody.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instGovernBody.new', {
                parent: 'instGovernBody',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGovernBody/instGovernBody-dialog.html',
                        controller: 'InstGovernBodyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    position: null,
                                    mobileNo: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instGovernBody', null, { reload: true });
                    }, function() {
                        $state.go('instGovernBody');
                    })
                }]
            })
            .state('instGovernBody.edit', {
                parent: 'instGovernBody',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGovernBody/instGovernBody-dialog.html',
                        controller: 'InstGovernBodyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstGovernBody', function(InstGovernBody) {
                                return InstGovernBody.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGovernBody', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instGovernBody.delete', {
                parent: 'instGovernBody',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGovernBody/instGovernBody-delete-dialog.html',
                        controller: 'InstGovernBodyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstGovernBody', function(InstGovernBody) {
                                return InstGovernBody.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGovernBody', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
