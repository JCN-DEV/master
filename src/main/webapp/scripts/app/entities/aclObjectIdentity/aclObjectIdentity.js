'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('aclObjectIdentity', {
                parent: 'entity',
                url: '/aclObjectIdentitys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aclObjectIdentity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aclObjectIdentity/aclObjectIdentitys.html',
                        controller: 'AclObjectIdentityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aclObjectIdentity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('aclObjectIdentity.detail', {
                parent: 'entity',
                url: '/aclObjectIdentity/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aclObjectIdentity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aclObjectIdentity/aclObjectIdentity-detail.html',
                        controller: 'AclObjectIdentityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aclObjectIdentity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AclObjectIdentity', function($stateParams, AclObjectIdentity) {
                        return AclObjectIdentity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('aclObjectIdentity.new', {
                parent: 'aclObjectIdentity',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aclObjectIdentity/aclObjectIdentity-dialog.html',
                        controller: 'AclObjectIdentityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    entriesInheriting: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('aclObjectIdentity', null, { reload: true });
                    }, function() {
                        $state.go('aclObjectIdentity');
                    })
                }]
            })
            .state('aclObjectIdentity.edit', {
                parent: 'aclObjectIdentity',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aclObjectIdentity/aclObjectIdentity-dialog.html',
                        controller: 'AclObjectIdentityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AclObjectIdentity', function(AclObjectIdentity) {
                                return AclObjectIdentity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aclObjectIdentity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('aclObjectIdentity.delete', {
                parent: 'aclObjectIdentity',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aclObjectIdentity/aclObjectIdentity-delete-dialog.html',
                        controller: 'AclObjectIdentityDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AclObjectIdentity', function(AclObjectIdentity) {
                                return AclObjectIdentity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aclObjectIdentity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
