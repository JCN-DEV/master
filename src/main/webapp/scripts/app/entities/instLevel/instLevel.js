'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instLevel', {
                parent: 'entity',
                url: '/instLevels',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instLevel.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instLevel/instLevels.html',
                        controller: 'InstLevelController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLevel');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instLevel.detail', {
                parent: 'entity',
                url: '/instLevel/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instLevel.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instLevel/instLevel-detail.html',
                        controller: 'InstLevelDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLevel');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstLevel', function($stateParams, InstLevel) {
                        return InstLevel.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instLevel.new', {
                parent: 'instLevel',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLevel/instLevel-dialog.html',
                        controller: 'InstLevelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    pStatus: null,
                                    createdDate: null,
                                    updatedDate: null,
                                    createdBy: null,
                                    updatedBy: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instLevel', null, { reload: true });
                    }, function() {
                        $state.go('instLevel');
                    })
                }]
            })
            .state('instLevel.edit', {
                parent: 'instLevel',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLevel/instLevel-dialog.html',
                        controller: 'InstLevelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstLevel', function(InstLevel) {
                                return InstLevel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instLevel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instLevel.delete', {
                parent: 'instLevel',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLevel/instLevel-delete-dialog.html',
                        controller: 'InstLevelDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstLevel', function(InstLevel) {
                                return InstLevel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instLevel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
