'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instLand', {
                parent: 'entity',
                url: '/instLands',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instLand.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instLand/instLands.html',
                        controller: 'InstLandController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLand');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instLand.detail', {
                parent: 'entity',
                url: '/instLand/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instLand.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instLand/instLand-detail.html',
                        controller: 'InstLandDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLand');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstLand', function($stateParams, InstLand) {
                        return InstLand.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instLand.new', {
                parent: 'instLand',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLand/instLand-dialog.html',
                        controller: 'InstLandDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    mouja: null,
                                    jlNo: null,
                                    ledgerNo: null,
                                    dagNo: null,
                                    amountOfLand: null,
                                    landRegistrationLedgerNo: null,
                                    landRegistrationDate: null,
                                    lastTaxPaymentDate: null,
                                    boundaryNorth: null,
                                    boundarySouth: null,
                                    boundaryEast: null,
                                    boundaryWest: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instLand', null, { reload: true });
                    }, function() {
                        $state.go('instLand');
                    })
                }]
            })
            .state('instLand.edit', {
                parent: 'instLand',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLand/instLand-dialog.html',
                        controller: 'InstLandDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstLand', function(InstLand) {
                                return InstLand.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instLand', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instLand.delete', {
                parent: 'instLand',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLand/instLand-delete-dialog.html',
                        controller: 'InstLandDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstLand', function(InstLand) {
                                return InstLand.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instLand', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
