'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instLevelTemp', {
                parent: 'entity',
                url: '/instLevelTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instLevelTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instLevelTemp/instLevelTemps.html',
                        controller: 'InstLevelTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLevelTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instLevelTemp.detail', {
                parent: 'entity',
                url: '/instLevelTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instLevelTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instLevelTemp/instLevelTemp-detail.html',
                        controller: 'InstLevelTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLevelTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstLevelTemp', function($stateParams, InstLevelTemp) {
                        return InstLevelTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instLevelTemp.new', {
                parent: 'instLevelTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLevelTemp/instLevelTemp-dialog.html',
                        controller: 'InstLevelTempDialogController',
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
                        $state.go('instLevelTemp', null, { reload: true });
                    }, function() {
                        $state.go('instLevelTemp');
                    })
                }]
            })
            .state('instLevelTemp.edit', {
                parent: 'instLevelTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLevelTemp/instLevelTemp-dialog.html',
                        controller: 'InstLevelTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstLevelTemp', function(InstLevelTemp) {
                                return InstLevelTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instLevelTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
