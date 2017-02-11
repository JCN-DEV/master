'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('aclEntry', {
                parent: 'entity',
                url: '/aclEntrys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aclEntry.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aclEntry/aclEntrys.html',
                        controller: 'AclEntryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aclEntry');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('aclEntry.detail', {
                parent: 'entity',
                url: '/aclEntry/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aclEntry.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aclEntry/aclEntry-detail.html',
                        controller: 'AclEntryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aclEntry');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AclEntry', function($stateParams, AclEntry) {
                        return AclEntry.get({id : $stateParams.id});
                    }]
                }
            })
            .state('aclEntry.new', {
                parent: 'aclEntry',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aclEntry/aclEntry-dialog.html',
                        controller: 'AclEntryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    aceOrder: null,
                                    mask: null,
                                    granting: false,
                                    auditSuccess: false,
                                    auditFailure: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('aclEntry', null, { reload: true });
                    }, function() {
                        $state.go('aclEntry');
                    })
                }]
            })
            .state('aclEntry.edit', {
                parent: 'aclEntry',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aclEntry/aclEntry-dialog.html',
                        controller: 'AclEntryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AclEntry', function(AclEntry) {
                                return AclEntry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aclEntry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('aclEntry.delete', {
                parent: 'aclEntry',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aclEntry/aclEntry-delete-dialog.html',
                        controller: 'AclEntryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AclEntry', function(AclEntry) {
                                return AclEntry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aclEntry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
