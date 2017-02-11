'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fee', {
                parent: 'entity',
                url: '/fees',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.fee.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fee/fees.html',
                        controller: 'FeeController'
                    },
                    'feeManagementView@fee':{
                        templateUrl: 'scripts/app/entities/fee/fee-dashboard.html',
                        controller: 'FeeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feePaymentCollection');
                        $translatePartialLoader.addPart('fee');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
    });


/*'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fee', {
                parent: 'entity',
                url: '/fees',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.fee.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fee/fees.html',
                        controller: 'FeeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fee');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('fee.detail', {
                parent: 'entity',
                url: '/fee/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.fee.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fee/fee-detail.html',
                        controller: 'FeeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fee');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Fee', function($stateParams, Fee) {
                        return Fee.get({id : $stateParams.id});
                    }]
                }
            })
            .state('fee.new', {
                parent: 'fee',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fee/fee-dialog.html',
                        controller: 'FeeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    feeId: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fee', null, { reload: true });
                    }, function() {
                        $state.go('fee');
                    })
                }]
            })
            .state('fee.edit', {
                parent: 'fee',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fee/fee-dialog.html',
                        controller: 'FeeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Fee', function(Fee) {
                                return Fee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fee', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
*/
