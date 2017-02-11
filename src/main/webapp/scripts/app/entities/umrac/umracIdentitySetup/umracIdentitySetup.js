'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('umracIdentitySetup', {
                parent: 'umrac',
                url: '/umracIdentitySetups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracIdentitySetup.home.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umracIdentitySetup/umracIdentitySetups.html',
                        controller: 'UmracIdentitySetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracIdentitySetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('umracIdentitySetup.detail', {
                parent: 'umrac',
                url: '/umracIdentitySetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracIdentitySetup.detail.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umracIdentitySetup/umracIdentitySetup-detail.html',
                        controller: 'UmracIdentitySetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracIdentitySetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UmracIdentitySetup', function($stateParams, UmracIdentitySetup) {
                        return UmracIdentitySetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('umracIdentitySetup.new', {
                parent: 'umracIdentitySetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracIdentitySetup/umracIdentitySetup-dialog.html',
                        controller: 'UmracIdentitySetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracIdentitySetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            empId: null,
                            userName: null,
                            uPw: null,
                            confm_Pw: null,
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
            .state('umracIdentitySetup.edit', {
                parent: 'umracIdentitySetup',
                url: '/edit/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.umracIdentitySetup.detail.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracIdentitySetup/umracIdentitySetup-dialog.html',
                        controller: 'UmracIdentitySetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracIdentitySetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UmracIdentitySetup', function ($stateParams, UmracIdentitySetup) {
                        return UmracIdentitySetup.get({id: $stateParams.id});
                    }]
                }
            })
            /*.state('umracIdentitySetup.new', {
                parent: 'umracIdentitySetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracIdentitySetup/umracIdentitySetup-dialog.html',
                        controller: 'UmracIdentitySetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    empId: null,
                                    userName: null,
                                    uPw: null,
                                    confm_Pw: null,
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
                        $state.go('umracIdentitySetup', null, { reload: true });
                    }, function() {
                        $state.go('umracIdentitySetup');
                    })
                }]
            })
            .state('umracIdentitySetup.edit', {
                parent: 'umracIdentitySetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracIdentitySetup/umracIdentitySetup-dialog.html',
                        controller: 'UmracIdentitySetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UmracIdentitySetup', function(UmracIdentitySetup) {
                                return UmracIdentitySetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('umracIdentitySetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
            ;
    });
