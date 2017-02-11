'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('aclSid', {
                parent: 'entity',
                url: '/aclSids',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aclSid.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aclSid/aclSids.html',
                        controller: 'AclSidController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aclSid');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('aclSid.detail', {
                parent: 'entity',
                url: '/aclSid/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aclSid.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aclSid/aclSid-detail.html',
                        controller: 'AclSidDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aclSid');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AclSid', function($stateParams, AclSid) {
                        return AclSid.get({id : $stateParams.id});
                    }]
                }
            })
            .state('aclSid.new', {
                parent: 'aclSid',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aclSid/aclSid-dialog.html',
                        controller: 'AclSidDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    principal: false,
                                    sid: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('aclSid', null, { reload: true });
                    }, function() {
                        $state.go('aclSid');
                    })
                }]
            })
            .state('aclSid.edit', {
                parent: 'aclSid',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aclSid/aclSid-dialog.html',
                        controller: 'AclSidDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AclSid', function(AclSid) {
                                return AclSid.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aclSid', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('aclSid.delete', {
                parent: 'aclSid',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aclSid/aclSid-delete-dialog.html',
                        controller: 'AclSidDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AclSid', function(AclSid) {
                                return AclSid.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aclSid', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
