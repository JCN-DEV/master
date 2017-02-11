'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteLand', {
                parent: 'entity',
                url: '/instituteLands',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteLand.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteLand/instituteLands.html',
                        controller: 'InstituteLandController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteLand');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteLand.detail', {
                parent: 'entity',
                url: '/instituteLand/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteLand.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteLand/instituteLand-detail.html',
                        controller: 'InstituteLandDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteLand');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstituteLand', function($stateParams, InstituteLand) {
                        return InstituteLand.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instituteLand.new', {
                parent: 'instituteLand',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteLand/instituteLand-dialog.html',
                        controller: 'InstituteLandDialogController',
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
                        $state.go('instituteLand', null, { reload: true });
                    }, function() {
                        $state.go('instituteLand');
                    })
                }]
            })
            .state('instituteLand.edit', {
                parent: 'instituteLand',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteLand/instituteLand-dialog.html',
                        controller: 'InstituteLandDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstituteLand', function(InstituteLand) {
                                return InstituteLand.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteLand', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instituteLand.delete', {
                parent: 'instituteLand',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteLand/instituteLand-delete-dialog.html',
                        controller: 'InstituteLandDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstituteLand', function(InstituteLand) {
                                return InstituteLand.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteLand', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
