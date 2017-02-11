'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('umracRightsSetup', {
                parent: 'entity',
                url: '/umracRightsSetups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracRightsSetup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/umracRightsSetup/umracRightsSetups.html',
                        controller: 'UmracRightsSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracRightsSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('umracRightsSetup.detail', {
                parent: 'entity',
                url: '/umracRightsSetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracRightsSetup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/umracRightsSetup/umracRightsSetup-detail.html',
                        controller: 'UmracRightsSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracRightsSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UmracRightsSetup', function($stateParams, UmracRightsSetup) {
                        return UmracRightsSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('umracRightsSetup.new', {
                parent: 'umracRightsSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracRightsSetup/umracRightsSetup-dialog.html',
                        controller: 'UmracRightsSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    rightId: null,
                                    roleId: null,
                                    module_id: null,
                                    subModule_id: null,
                                    rights: null,
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
                        $state.go('umracRightsSetup', null, { reload: true });
                    }, function() {
                        $state.go('umracRightsSetup');
                    })
                }]
            })
            .state('umracRightsSetup.edit', {
                parent: 'umracRightsSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracRightsSetup/umracRightsSetup-dialog.html',
                        controller: 'UmracRightsSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UmracRightsSetup', function(UmracRightsSetup) {
                                return UmracRightsSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('umracRightsSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
