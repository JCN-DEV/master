'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('umracSubmoduleSetup', {
                parent: 'umrac',
                url: '/umracSubmoduleSetups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracSubmoduleSetup.home.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracSubmoduleSetup/umracSubmoduleSetups.html',
                        controller: 'UmracSubmoduleSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracSubmoduleSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('umracSubmoduleSetup.detail', {
                parent: 'umrac',
                url: '/umracSubmoduleSetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracSubmoduleSetup.detail.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracSubmoduleSetup/umracSubmoduleSetup-detail.html',
                        controller: 'UmracSubmoduleSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracSubmoduleSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UmracSubmoduleSetup', function($stateParams, UmracSubmoduleSetup) {
                        return UmracSubmoduleSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('umracSubmoduleSetup.new', {
                parent: 'umracSubmoduleSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracSubmoduleSetup/umracSubmoduleSetup-dialog.html',
                        controller: 'UmracSubmoduleSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracSubmoduleSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            subModuleId: null,
                            subModuleName: null,
                            subModuleUrl: null,
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
            .state('umracSubmoduleSetup.edit', {
                parent: 'umracSubmoduleSetup',
                url: '/edit/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.umracSubmoduleSetup.detail.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracSubmoduleSetup/umracSubmoduleSetup-dialog.html',
                        controller: 'UmracSubmoduleSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracSubmoduleSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UmracSubmoduleSetup', function ($stateParams, UmracSubmoduleSetup) {
                        return UmracSubmoduleSetup.get({id: $stateParams.id});
                    }]
                }
            })
            /*.state('umracSubmoduleSetup.new', {
                parent: 'umracSubmoduleSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracSubmoduleSetup/umracSubmoduleSetup-dialog.html',
                        controller: 'UmracSubmoduleSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    subModuleId: null,
                                    subModuleName: null,
                                    subModuleUrl: null,
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
                        $state.go('umracSubmoduleSetup', null, { reload: true });
                    }, function() {
                        $state.go('umracSubmoduleSetup');
                    })
                }]
            })
            .state('umracSubmoduleSetup.edit', {
                parent: 'umracSubmoduleSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracSubmoduleSetup/umracSubmoduleSetup-dialog.html',
                        controller: 'UmracSubmoduleSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UmracSubmoduleSetup', function(UmracSubmoduleSetup) {
                                return UmracSubmoduleSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('umracSubmoduleSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
            ;
    });
