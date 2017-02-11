'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dlContTypeSet', {
                parent: 'entity',
                url: '/dlContTypeSets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlContTypeSet.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlContTypeSet/dlContTypeSets.html',
                        controller: 'DlContTypeSetController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContTypeSet');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dlContTypeSet.detail', {
                parent: 'entity',
                url: '/dlContTypeSet/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlContTypeSet.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlContTypeSet/dlContTypeSet-detail.html',
                        controller: 'DlContTypeSetDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContTypeSet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlContTypeSet', function($stateParams, DlContTypeSet) {
                        return DlContTypeSet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dlContTypeSet.new', {
                parent: 'dlContTypeSet',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlContTypeSet/dlContTypeSet-dialog.html',
                        controller: 'DlContTypeSetDialogController',
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
                        $state.go('dlContTypeSet', null, { reload: true });
                    }, function() {
                        $state.go('dlContTypeSet');
                    })
                }]
            })
            .state('dlContTypeSet.edit', {
                parent: 'dlContTypeSet',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlContTypeSet/dlContTypeSet-dialog.html',
                        controller: 'DlContTypeSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlContTypeSet', function(DlContTypeSet) {
                                return DlContTypeSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContTypeSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('dlContTypeSet.delete', {
                parent: 'dlContTypeSet',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlContTypeSet/dlContTypeSet-delete-dialog.html',
                        controller: 'DlContTypeSetDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlContTypeSet', function(DlContTypeSet) {
                                return DlContTypeSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContTypeSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
