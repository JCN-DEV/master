'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('umracModuleSetup', {
                parent: 'umrac',
                url: '/umracModuleSetups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracModuleSetup.home.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracModuleSetup/umracModuleSetups.html',
                        controller: 'UmracModuleSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracModuleSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('umracModuleSetup.detail', {
                parent: 'umrac',
                url: '/umracModuleSetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracModuleSetup.detail.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracModuleSetup/umracModuleSetup-detail.html',
                        controller: 'UmracModuleSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracModuleSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UmracModuleSetup', function($stateParams, UmracModuleSetup) {
                        return UmracModuleSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('umracModuleSetup.new', {
                parent: 'umracModuleSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracModuleSetup/umracModuleSetup-dialog.html',
                        controller: 'UmracModuleSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracModuleSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            moduleId: null,
                            moduleName: null,
                            moduleUrl: null,
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
            .state('umracModuleSetup.edit', {
                parent: 'umracModuleSetup',
                url: '/edit/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.umracModuleSetup.detail.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracModuleSetup/umracModuleSetup-dialog.html',
                        controller: 'UmracModuleSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracModuleSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UmracModuleSetup', function ($stateParams, UmracModuleSetup) {
                        return UmracModuleSetup.get({id: $stateParams.id});
                    }]
                }
            })
            /*.state('umracModuleSetup.new', {
                parent: 'umracModuleSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracModuleSetup/umracModuleSetup-dialog.html',
                        controller: 'UmracModuleSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    moduleId: null,
                                    moduleName: null,
                                    moduleUrl: null,
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
                        $state.go('umracModuleSetup', null, { reload: true });
                    }, function() {
                        $state.go('umracModuleSetup');
                    })
                }]
            })
            .state('umracModuleSetup.edit', {
                parent: 'umracModuleSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracModuleSetup/umracModuleSetup-dialog.html',
                        controller: 'UmracModuleSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UmracModuleSetup', function(UmracModuleSetup) {
                                return UmracModuleSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('umracModuleSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
            ;
    });
