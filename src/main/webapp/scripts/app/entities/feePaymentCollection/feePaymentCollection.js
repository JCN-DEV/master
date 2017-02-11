'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('feePaymentCollection', {
                parent: 'entity',
                url: '/feePaymentCollections',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.feePaymentCollection.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feePaymentCollection/feePaymentCollections.html',
                        controller: 'FeePaymentCollectionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feePaymentCollection');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('feePaymentCollection.detail', {
                parent: 'entity',
                url: '/feePaymentCollection/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.feePaymentCollection.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feePaymentCollection/feePaymentCollection-detail.html',
                        controller: 'FeePaymentCollectionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feePaymentCollection');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'FeePaymentCollection', function($stateParams, FeePaymentCollection) {
                        return FeePaymentCollection.get({id : $stateParams.id});
                    }]
                }
            })
            .state('feePaymentCollection.new', {
                parent: 'feePaymentCollection',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feePaymentCollection/feePaymentCollection-dialog.html',
                        controller: 'FeePaymentCollectionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    transactionId: null,
                                    voucherNo: null,
                                    status: null,
                                    description: null,
                                    amount: null,
                                    createDate: null,
                                    createBy: null,
                                    paymentDate: null,
                                    bankName: null,
                                    bankAccountNo: null,
                                    paymentMethod: null,
                                    paymentApiID: null,
                                    paymentInstrumentType: null,
                                    currency: null,
                                    updatedBy: null,
                                    updatedTime: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('feePaymentCollection', null, { reload: true });
                    }, function() {
                        $state.go('feePaymentCollection');
                    })
                }]
            })
            .state('feePaymentCollection.edit', {
                parent: 'feePaymentCollection',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feePaymentCollection/feePaymentCollection-dialog.html',
                        controller: 'FeePaymentCollectionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['FeePaymentCollection', function(FeePaymentCollection) {
                                return FeePaymentCollection.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('feePaymentCollection', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
