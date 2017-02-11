'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dlContUpld', {
                parent: 'entity',
                url: '/dlContUplds',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlContUpld.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlContUpld/dlContUplds.html',
                        controller: 'DlContUpldController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContUpld');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dlContUpld.detail', {
                parent: 'entity',
                url: '/dlContUpld/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlContUpld.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlContUpld/dlContUpld-detail.html',
                        controller: 'DlContUpldDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContUpld');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlContUpld', function($stateParams, DlContUpld) {
                        return DlContUpld.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dlContUpld.new', {
                parent: 'dlContUpld',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlContUpld/dlContUpld-dialog.html',
                        controller: 'DlContUpldDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    authName: null,
                                    edition: null,
                                    isbnNo: null,
                                    copyright: null,
                                    publisher: null,
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
                        $state.go('dlContUpld', null, { reload: true });
                    }, function() {
                        $state.go('dlContUpld');
                    })
                }]
            })
            .state('dlContUpld.edit', {
                parent: 'dlContUpld',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlContUpld/dlContUpld-dialog.html',
                        controller: 'DlContUpldDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlContUpld', function(DlContUpld) {
                                return DlContUpld.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContUpld', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('dlContUpld.delete', {
                parent: 'dlContUpld',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlContUpld/dlContUpld-delete-dialog.html',
                        controller: 'DlContUpldDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlContUpld', function(DlContUpld) {
                                return DlContUpld.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContUpld', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
