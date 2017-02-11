'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('umracActionSetup', {
                parent: 'entity',
                url: '/umracActionSetups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracActionSetup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/umracActionSetup/umracActionSetups.html',
                        controller: 'UmracActionSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracActionSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('umracActionSetup.detail', {
                parent: 'entity',
                url: '/umracActionSetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracActionSetup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/umracActionSetup/umracActionSetup-detail.html',
                        controller: 'UmracActionSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracActionSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UmracActionSetup', function($stateParams, UmracActionSetup) {
                        return UmracActionSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('umracActionSetup.new', {
                parent: 'umracActionSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracActionSetup/umracActionSetup-dialog.html',
                        controller: 'UmracActionSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    actionId: null,
                                    actionName: null,
                                    actionUrl: null,
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
                        $state.go('umracActionSetup', null, { reload: true });
                    }, function() {
                        $state.go('umracActionSetup');
                    })
                }]
            })
            .state('umracActionSetup.edit', {
                parent: 'umracActionSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracActionSetup/umracActionSetup-dialog.html',
                        controller: 'UmracActionSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UmracActionSetup', function(UmracActionSetup) {
                                return UmracActionSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('umracActionSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
