'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dlContCatSet', {
                parent: 'entity',
                url: '/dlContCatSets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlContCatSet.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlContCatSet/dlContCatSets.html',
                        controller: 'DlContCatSetController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContCatSet');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dlContCatSet.detail', {
                parent: 'entity',
                url: '/dlContCatSet/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlContCatSet.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlContCatSet/dlContCatSet-detail.html',
                        controller: 'DlContCatSetDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContCatSet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlContCatSet', function($stateParams, DlContCatSet) {
                        return DlContCatSet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dlContCatSet.new', {
                parent: 'dlContCatSet',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlContCatSet/dlContCatSet-dialog.html',
                        controller: 'DlContCatSetDialogController',
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
                        $state.go('dlContCatSet', null, { reload: true });
                    }, function() {
                        $state.go('dlContCatSet');
                    })
                }]
            })
            .state('dlContCatSet.edit', {
                parent: 'dlContCatSet',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlContCatSet/dlContCatSet-dialog.html',
                        controller: 'DlContCatSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlContCatSet', function(DlContCatSet) {
                                return DlContCatSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContCatSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('dlContCatSet.delete', {
                parent: 'dlContCatSet',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlContCatSet/dlContCatSet-delete-dialog.html',
                        controller: 'DlContCatSetDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlContCatSet', function(DlContCatSet) {
                                return DlContCatSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContCatSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
