'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instPlayGroundInfo', {
                parent: 'entity',
                url: '/instPlayGroundInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instPlayGroundInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instPlayGroundInfo/instPlayGroundInfos.html',
                        controller: 'InstPlayGroundInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instPlayGroundInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instPlayGroundInfo.detail', {
                parent: 'entity',
                url: '/instPlayGroundInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instPlayGroundInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instPlayGroundInfo/instPlayGroundInfo-detail.html',
                        controller: 'InstPlayGroundInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instPlayGroundInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstPlayGroundInfo', function($stateParams, InstPlayGroundInfo) {
                        return InstPlayGroundInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instPlayGroundInfo.new', {
                parent: 'instPlayGroundInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instPlayGroundInfo/instPlayGroundInfo-dialog.html',
                        controller: 'InstPlayGroundInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    playgroundNo: null,
                                    area: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instPlayGroundInfo', null, { reload: true });
                    }, function() {
                        $state.go('instPlayGroundInfo');
                    })
                }]
            })
            .state('instPlayGroundInfo.edit', {
                parent: 'instPlayGroundInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instPlayGroundInfo/instPlayGroundInfo-dialog.html',
                        controller: 'InstPlayGroundInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstPlayGroundInfo', function(InstPlayGroundInfo) {
                                return InstPlayGroundInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instPlayGroundInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instPlayGroundInfo.delete', {
                parent: 'instPlayGroundInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instPlayGroundInfo/instPlayGroundInfo-delete-dialog.html',
                        controller: 'InstPlayGroundInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstPlayGroundInfo', function(InstPlayGroundInfo) {
                                return InstPlayGroundInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instPlayGroundInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
