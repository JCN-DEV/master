'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instShopInfo', {
                parent: 'entity',
                url: '/instShopInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instShopInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instShopInfo/instShopInfos.html',
                        controller: 'InstShopInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instShopInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instShopInfo.detail', {
                parent: 'entity',
                url: '/instShopInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instShopInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instShopInfo/instShopInfo-detail.html',
                        controller: 'InstShopInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instShopInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstShopInfo', function($stateParams, InstShopInfo) {
                        return InstShopInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instShopInfo.new', {
                parent: 'instShopInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instShopInfo/instShopInfo-dialog.html',
                        controller: 'InstShopInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nameOrNumber: null,
                                    buildingNameOrNumber: null,
                                    length: null,
                                    width: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instShopInfo', null, { reload: true });
                    }, function() {
                        $state.go('instShopInfo');
                    })
                }]
            })
            .state('instShopInfo.edit', {
                parent: 'instShopInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instShopInfo/instShopInfo-dialog.html',
                        controller: 'InstShopInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstShopInfo', function(InstShopInfo) {
                                return InstShopInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instShopInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instShopInfo.delete', {
                parent: 'instShopInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instShopInfo/instShopInfo-delete-dialog.html',
                        controller: 'InstShopInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstShopInfo', function(InstShopInfo) {
                                return InstShopInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instShopInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
