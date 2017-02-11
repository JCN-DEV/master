'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('umracRoleAssignUser', {
                parent: 'umrac',
                url: '/umracRoleAssignUsers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracRoleAssignUser.home.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracRoleAssignUser/umracRoleAssignUsers.html',
                        controller: 'UmracRoleAssignUserController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracRoleAssignUser');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('umracRoleAssignUser.detail', {
                parent: 'umrac',
                url: '/umracRoleAssignUser/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracRoleAssignUser.detail.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracRoleAssignUser/umracRoleAssignUser-detail.html',
                        controller: 'UmracRoleAssignUserDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracRoleAssignUser');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UmracRoleAssignUser', function($stateParams, UmracRoleAssignUser) {
                        return UmracRoleAssignUser.get({id : $stateParams.id});
                    }]
                }
            })
            .state('umracRoleAssignUser.new', {
                parent: 'umracRoleAssignUser',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracRoleAssignUser/umracRoleAssignUser-dialog.html',
                        controller: 'UmracRoleAssignUserDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracActionSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            description: null,
                            status: true,
                            createDate: null,
                            createBy: null,
                            updatedBy: null,
                            updatedTime: null,
                            id: null
                        };
                    }
                }
            })
            .state('umracRoleAssignUser.edit', {
                parent: 'umracRoleAssignUser',
                url: '/edit/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.umracActionSetup.detail.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracRoleAssignUser/umracRoleAssignUser-dialog.html',
                        controller: 'UmracRoleAssignUserDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracRoleAssignUser');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UmracRoleAssignUser', function ($stateParams, UmracRoleAssignUser) {
                        return UmracRoleAssignUser.get({id: $stateParams.id});
                    }]
                }
            })
            /*.state('umracRoleAssignUser.new', {
                parent: 'umracRoleAssignUser',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracRoleAssignUser/umracRoleAssignUser-dialog.html',
                        controller: 'UmracRoleAssignUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    description: null,
                                    status: true,
                                    createDate: null,
                                    createBy: null,
                                    updatedBy: null,
                                    updatedTime: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('umracRoleAssignUser', null, { reload: true });
                    }, function() {
                        $state.go('umracRoleAssignUser');
                    })
                }]
            })
            .state('umracRoleAssignUser.edit', {
                parent: 'umracRoleAssignUser',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracRoleAssignUser/umracRoleAssignUser-dialog.html',
                        controller: 'UmracRoleAssignUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UmracRoleAssignUser', function(UmracRoleAssignUser) {
                                return UmracRoleAssignUser.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('umracRoleAssignUser', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
            ;
    });
