'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('feeInvoice', {
                parent: 'entity',
                url: '/feeInvoices',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.feeInvoice.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feeInvoice/feeInvoices.html',
                        controller: 'FeeInvoiceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feeInvoice');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('feeInvoice.detail', {
                parent: 'entity',
                url: '/feeInvoice/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.feeInvoice.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feeInvoice/feeInvoice-detail.html',
                        controller: 'FeeInvoiceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feeInvoice');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'FeeInvoice', function($stateParams, FeeInvoice) {
                        return FeeInvoice.get({id : $stateParams.id});
                    }]
                }
            })
            .state('feeInvoice.new', {
                parent: 'feeInvoice',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feeInvoice/feeInvoice-dialog.html',
                        controller: 'FeeInvoiceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    invoiceId: null,
                                    bankName: null,
                                    bankAccountNo: null,
                                    description: null,
                                    createDate: null,
                                    createBy: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('feeInvoice', null, { reload: true });
                    }, function() {
                        $state.go('feeInvoice');
                    })
                }]
            })
            .state('feeInvoice.edit', {
                parent: 'feeInvoice',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feeInvoice/feeInvoice-dialog.html',
                        controller: 'FeeInvoiceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['FeeInvoice', function(FeeInvoice) {
                                return FeeInvoice.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('feeInvoice', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
