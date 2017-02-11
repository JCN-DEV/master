//'use strict';
//
//angular.module('stepApp')
//    .config(function ($stateProvider) {
//        $stateProvider
//            .state('umracRoleSetup', {
//                parent: 'entity',
//                url: '/umracRoleSetups',
//                data: {
//                    authorities: ['ROLE_USER'],
//                    pageTitle: 'stepApp.umracRoleSetup.home.title'
//                },
//                views: {
//                    'content@': {
//                        templateUrl: 'scripts/app/entities/umracRoleSetup/umracRoleSetups.html',
//                        controller: 'UmracRoleSetupController'
//                    }
//                },
//                resolve: {
//                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                        $translatePartialLoader.addPart('umracRoleSetup');
//                        $translatePartialLoader.addPart('global');
//                        return $translate.refresh();
//                    }]
//                }
//            })
//            .state('umracRoleSetup.detail', {
//                parent: 'entity',
//                url: '/umracRoleSetup/{id}',
//                data: {
//                    authorities: ['ROLE_USER'],
//                    pageTitle: 'stepApp.umracRoleSetup.detail.title'
//                },
//                views: {
//                    'content@': {
//                        templateUrl: 'scripts/app/entities/umracRoleSetup/umracRoleSetup-detail.html',
//                        controller: 'UmracRoleSetupDetailController'
//                    }
//                },
//                resolve: {
//                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                        $translatePartialLoader.addPart('umracRoleSetup');
//                        return $translate.refresh();
//                    }],
//                    entity: ['$stateParams', 'UmracRoleSetup', function($stateParams, UmracRoleSetup) {
//                        return UmracRoleSetup.get({id : $stateParams.id});
//                    }]
//                }
//            })
//            .state('umracRoleSetup.new', {
//                parent: 'umracRoleSetup',
//                url: '/new',
//                data: {
//                    authorities: ['ROLE_USER'],
//                },
//                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
//                    $modal.open({
//                        templateUrl: 'scripts/app/entities/umracRoleSetup/umracRoleSetup-dialog.html',
//                        controller: 'UmracRoleSetupDialogController',
//                        size: 'lg',
//                        resolve: {
//                            entity: function () {
//                                return {
//                                    roleId: null,
//                                    roleName: null,
//                                    description: null,
//                                    status: true,
//                                    createDate: null,
//                                    createBy: null,
//                                    updatedBy: null,
//                                    updatedTime: null,
//                                    id: null
//                                };
//                            }
//                        }
//                    }).result.then(function(result) {
//                        $state.go('umracRoleSetup', null, { reload: true });
//                    }, function() {
//                        $state.go('umracRoleSetup');
//                    })
//                }]
//            })
//            .state('umracRoleSetup.edit', {
//                parent: 'umracRoleSetup',
//                url: '/{id}/edit',
//                data: {
//                    authorities: ['ROLE_USER'],
//                },
//                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
//                    $modal.open({
//                        templateUrl: 'scripts/app/entities/umracRoleSetup/umracRoleSetup-dialog.html',
//                        controller: 'UmracRoleSetupDialogController',
//                        size: 'lg',
//                        resolve: {
//                            entity: ['UmracRoleSetup', function(UmracRoleSetup) {
//                                return UmracRoleSetup.get({id : $stateParams.id});
//                            }]
//                        }
//                    }).result.then(function(result) {
//                        $state.go('umracRoleSetup', null, { reload: true });
//                    }, function() {
//                        $state.go('^');
//                    })
//                }]
//            });
//    });
