'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('feePaymentTypeSetup', {
                parent: 'entity',
                url: '/feePaymentTypeSetups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.feePaymentTypeSetup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feePaymentTypeSetup/feePaymentTypeSetups.html',
                        controller: 'FeePaymentTypeSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feePaymentTypeSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('feePaymentTypeSetup.detail', {
                parent: 'entity',
                url: '/feePaymentTypeSetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.feePaymentTypeSetup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feePaymentTypeSetup/feePaymentTypeSetup-detail.html',
                        controller: 'FeePaymentTypeSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feePaymentTypeSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'FeePaymentTypeSetup', function($stateParams, FeePaymentTypeSetup) {
                        return FeePaymentTypeSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('feePaymentTypeSetup.new', {
                parent: 'feePaymentTypeSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feePaymentTypeSetup/feePaymentTypeSetup-dialog.html',
                        controller: 'FeePaymentTypeSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    paymentTypeId: null,
                                    name: null,
                                    status: null,
                                    description: null,
                                    createDate: null,
                                    createBy: null,
                                    updateBy: null,
                                    updateDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('feePaymentTypeSetup', null, { reload: true });
                    }, function() {
                        $state.go('feePaymentTypeSetup');
                    })
                }]
            })
            .state('feePaymentTypeSetup.edit', {
                parent: 'feePaymentTypeSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feePaymentTypeSetup/feePaymentTypeSetup-dialog.html',
                        controller: 'FeePaymentTypeSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['FeePaymentTypeSetup', function(FeePaymentTypeSetup) {
                                return FeePaymentTypeSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('feePaymentTypeSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
