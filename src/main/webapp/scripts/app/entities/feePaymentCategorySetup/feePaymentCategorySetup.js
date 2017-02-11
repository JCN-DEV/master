'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('feePaymentCategorySetup', {
                parent: 'entity',
                url: '/feePaymentCategorySetups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.feePaymentCategorySetup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feePaymentCategorySetup/feePaymentCategorySetups.html',
                        controller: 'FeePaymentCategorySetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feePaymentCategorySetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('feePaymentCategorySetup.detail', {
                parent: 'entity',
                url: '/feePaymentCategorySetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.feePaymentCategorySetup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feePaymentCategorySetup/feePaymentCategorySetup-detail.html',
                        controller: 'FeePaymentCategorySetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feePaymentCategorySetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'FeePaymentCategorySetup', function($stateParams, FeePaymentCategorySetup) {
                        return FeePaymentCategorySetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('feePaymentCategorySetup.new', {
                parent: 'feePaymentCategorySetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feePaymentCategorySetup/feePaymentCategorySetup-dialog.html',
                        controller: 'FeePaymentCategorySetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    paymentCategoryId: null,
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
                        $state.go('feePaymentCategorySetup', null, { reload: true });
                    }, function() {
                        $state.go('feePaymentCategorySetup');
                    })
                }]
            })
            .state('feePaymentCategorySetup.edit', {
                parent: 'feePaymentCategorySetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feePaymentCategorySetup/feePaymentCategorySetup-dialog.html',
                        controller: 'FeePaymentCategorySetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['FeePaymentCategorySetup', function(FeePaymentCategorySetup) {
                                return FeePaymentCategorySetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('feePaymentCategorySetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
