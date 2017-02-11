'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('gazetteSetup', {
                parent: 'entity',
                url: '/gazetteSetups',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.gazetteSetup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gazetteSetup/gazetteSetups.html',
                        controller: 'GazetteSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('gazetteSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('gazetteSetup.detail', {
                parent: 'entity',
                url: '/gazetteSetup/{id}',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.gazetteSetup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gazetteSetup/gazetteSetup-detail.html',
                        controller: 'GazetteSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('gazetteSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GazetteSetup', function($stateParams, GazetteSetup) {
                        return GazetteSetup.get({id : $stateParams.id});
                    }]
                }
            })

            .state('gazetteSetup.new', {
                parent: 'gazetteSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gazetteSetup/gazetteSetup-dialog.html',
                        controller: 'GazetteSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('gazetteSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', function($stateParams) {
                        return {
                            name: null,
                            status: null,
                            createBy: null,
                            createDate: null,
                            updateBy: null,
                            updateDate: null,
                            remarks: null,
                            id: null
                        };
                    }]
                }
            })

            /*.state('gazetteSetup.new', {
                parent: 'gazetteSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/gazetteSetup/gazetteSetup-dialog.html',
                        controller: 'GazetteSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    status: null,
                                    createBy: null,
                                    createDate: null,
                                    updateBy: null,
                                    updateDate: null,
                                    remarks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('gazetteSetup', null, { reload: true });
                    }, function() {
                        $state.go('gazetteSetup');
                    })
                }]
            })*/
            .state('gazetteSetup.edit', {
                parent: 'gazetteSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gazetteSetup/gazetteSetup-dialog.html',
                        controller: 'GazetteSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('gazetteSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GazetteSetup', function($stateParams, GazetteSetup) {
                        return GazetteSetup.get({id : $stateParams.id});
                    }]
                }
            })

            /*.state('gazetteSetup.edit', {
                parent: 'gazetteSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/gazetteSetup/gazetteSetup-dialog.html',
                        controller: 'GazetteSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GazetteSetup', function(GazetteSetup) {
                                return GazetteSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('gazetteSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });*/
    });
