'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instShopInfoTemp', {
                parent: 'entity',
                url: '/instShopInfoTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instShopInfoTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instShopInfoTemp/instShopInfoTemps.html',
                        controller: 'InstShopInfoTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instShopInfoTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instShopInfoTemp.detail', {
                parent: 'entity',
                url: '/instShopInfoTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instShopInfoTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instShopInfoTemp/instShopInfoTemp-detail.html',
                        controller: 'InstShopInfoTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instShopInfoTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstShopInfoTemp', function($stateParams, InstShopInfoTemp) {
                        return InstShopInfoTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instShopInfoTemp.new', {
                parent: 'instShopInfoTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instShopInfoTemp/instShopInfoTemp-dialog.html',
                        controller: 'InstShopInfoTempDialogController',
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
                        $state.go('instShopInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('instShopInfoTemp');
                    })
                }]
            })
            .state('instShopInfoTemp.edit', {
                parent: 'instShopInfoTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instShopInfoTemp/instShopInfoTemp-dialog.html',
                        controller: 'InstShopInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstShopInfoTemp', function(InstShopInfoTemp) {
                                return InstShopInfoTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instShopInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
