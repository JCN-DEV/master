/*
'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cmsTrade', {
                parent: 'entity',
                url: '/cmsTrades',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.cmsTrade.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsTrade/cmsTrades.html',
                        controller: 'CmsTradeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsTrade');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cmsTrade.detail', {
                parent: 'entity',
                url: '/cmsTrade/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.cmsTrade.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsTrade/cmsTrade-detail.html',
                        controller: 'CmsTradeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsTrade');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CmsTrade', function($stateParams, CmsTrade) {
                        return CmsTrade.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cmsTrade.new', {
                parent: 'cmsTrade',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsTrade/cmsTrade-dialog.html',
                        controller: 'CmsTradeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cmsTrade', null, { reload: true });
                    }, function() {
                        $state.go('cmsTrade');
                    })
                }]
            })
            .state('cmsTrade.edit', {
                parent: 'cmsTrade',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsTrade/cmsTrade-dialog.html',
                        controller: 'CmsTradeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CmsTrade', function(CmsTrade) {
                                return CmsTrade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsTrade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cmsTrade.delete', {
                parent: 'cmsTrade',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsTrade/cmsTrade-delete-dialog.html',
                        controller: 'CmsTradeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CmsTrade', function(CmsTrade) {
                                return CmsTrade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsTrade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
*/
