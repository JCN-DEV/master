'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('aclClass', {
                parent: 'entity',
                url: '/aclClasss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aclClass.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aclClass/aclClasss.html',
                        controller: 'AclClassController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aclClass');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('aclClass.detail', {
                parent: 'entity',
                url: '/aclClass/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aclClass.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aclClass/aclClass-detail.html',
                        controller: 'AclClassDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aclClass');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AclClass', function($stateParams, AclClass) {
                        return AclClass.get({id : $stateParams.id});
                    }]
                }
            })
            .state('aclClass.new', {
                parent: 'aclClass',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aclClass/aclClass-dialog.html',
                        controller: 'AclClassDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    aclClass: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('aclClass', null, { reload: true });
                    }, function() {
                        $state.go('aclClass');
                    })
                }]
            })
            .state('aclClass.edit', {
                parent: 'aclClass',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aclClass/aclClass-dialog.html',
                        controller: 'AclClassDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AclClass', function(AclClass) {
                                return AclClass.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aclClass', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('aclClass.delete', {
                parent: 'aclClass',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aclClass/aclClass-delete-dialog.html',
                        controller: 'AclClassDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AclClass', function(AclClass) {
                                return AclClass.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aclClass', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
