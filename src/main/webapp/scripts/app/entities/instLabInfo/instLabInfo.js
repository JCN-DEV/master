'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instLabInfo', {
                parent: 'entity',
                url: '/instLabInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instLabInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instLabInfo/instLabInfos.html',
                        controller: 'InstLabInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLabInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instLabInfo.detail', {
                parent: 'entity',
                url: '/instLabInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instLabInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instLabInfo/instLabInfo-detail.html',
                        controller: 'InstLabInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLabInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstLabInfo', function($stateParams, InstLabInfo) {
                        return InstLabInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instLabInfo.new', {
                parent: 'instLabInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLabInfo/instLabInfo-dialog.html',
                        controller: 'InstLabInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nameOrNumber: null,
                                    buildingNameOrNumber: null,
                                    length: null,
                                    width: null,
                                    totalBooks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instLabInfo', null, { reload: true });
                    }, function() {
                        $state.go('instLabInfo');
                    })
                }]
            })
            .state('instLabInfo.edit', {
                parent: 'instLabInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLabInfo/instLabInfo-dialog.html',
                        controller: 'InstLabInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstLabInfo', function(InstLabInfo) {
                                return InstLabInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instLabInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instLabInfo.delete', {
                parent: 'instLabInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLabInfo/instLabInfo-delete-dialog.html',
                        controller: 'InstLabInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstLabInfo', function(InstLabInfo) {
                                return InstLabInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instLabInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
