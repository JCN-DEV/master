'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instLandTemp', {
                parent: 'entity',
                url: '/instLandTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instLandTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instLandTemp/instLandTemps.html',
                        controller: 'InstLandTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLandTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instLandTemp.detail', {
                parent: 'entity',
                url: '/instLandTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instLandTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instLandTemp/instLandTemp-detail.html',
                        controller: 'InstLandTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLandTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstLandTemp', function($stateParams, InstLandTemp) {
                        return InstLandTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instLandTemp.new', {
                parent: 'instLandTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLandTemp/instLandTemp-dialog.html',
                        controller: 'InstLandTempDialogController',
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
                        $state.go('instLandTemp', null, { reload: true });
                    }, function() {
                        $state.go('instLandTemp');
                    })
                }]
            })
            .state('instLandTemp.edit', {
                parent: 'instLandTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLandTemp/instLandTemp-dialog.html',
                        controller: 'InstLandTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstLandTemp', function(InstLandTemp) {
                                return InstLandTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instLandTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
