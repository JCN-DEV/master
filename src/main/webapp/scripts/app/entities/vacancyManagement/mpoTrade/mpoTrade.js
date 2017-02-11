'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mpoTrade', {
                parent: 'mpo',
                url: '/mpoTrades',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.mpoTrade.home.title'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoTrade/mpoTrades.html',
                        controller: 'MpoTradeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoTrade');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoTrade.detail', {
                parent: 'mpoTrade',
                url: '/mpoTrade/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.mpoTrade.detail.title'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoTrade/mpoTrade-detail.html',
                        controller: 'MpoTradeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoTrade');
                        return $translate.refresh();
                    }]/*,
                    entity: ['$stateParams', 'MpoTrade', function($stateParams, MpoTrade) {
                        return MpoTrade.get({id : $stateParams.id});
                    }]*/
                }
            })
            .state('mpoTrade.new', {
                parent: 'mpoTrade',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoTrade/mpoTrade-dialog.html',
                        controller: 'MpoTradeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoTrade');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoTrade', function($stateParams, MpoTrade) {
                        //return MpoTrade.get({id : $stateParams.id});
                    }]
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoTrade/mpoTrade-dialog.html',
                        controller: 'MpoTradeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    cratedDate: null,
                                    updateDate: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('mpoTrade', null, { reload: true });
                    }, function() {
                        $state.go('mpoTrade');
                    })
                }]*/
            })
            .state('mpoTrade.edit', {
                parent: 'mpoTrade',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoTrade/mpoTrade-dialog.html',
                        controller: 'MpoTradeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoTrade');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoTrade', function($stateParams, MpoTrade) {
                        return MpoTrade.get({id : $stateParams.id});
                    }]
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoTrade/mpoTrade-dialog.html',
                        controller: 'MpoTradeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MpoTrade', function(MpoTrade) {
                                return MpoTrade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoTrade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('mpoTrade.delete', {
                parent: 'mpoTrade',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoTrade/mpoTrade-delete-dialog.html',
                        controller: 'MpoTradeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['MpoTrade', function(MpoTrade) {
                                return MpoTrade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoTrade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
