'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dlContSCatSet', {
                parent: 'entity',
                url: '/dlContSCatSets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlContSCatSet.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlContSCatSet/dlContSCatSets.html',
                        controller: 'DlContSCatSetController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContSCatSet');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dlContSCatSet.detail', {
                parent: 'entity',
                url: '/dlContSCatSet/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlContSCatSet.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlContSCatSet/dlContSCatSet-detail.html',
                        controller: 'DlContSCatSetDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContSCatSet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlContSCatSet', function($stateParams, DlContSCatSet) {
                        return DlContSCatSet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dlContSCatSet.new', {
                parent: 'dlContSCatSet',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlContSCatSet/dlContSCatSet-dialog.html',
                        controller: 'DlContSCatSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    pStatus: null,
                                    createdDate: null,
                                    updatedDate: null,
                                    createdBy: null,
                                    updatedBy: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dlContSCatSet', null, { reload: true });
                    }, function() {
                        $state.go('dlContSCatSet');
                    })
                }]
            })
            .state('dlContSCatSet.edit', {
                parent: 'dlContSCatSet',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlContSCatSet/dlContSCatSet-dialog.html',
                        controller: 'DlContSCatSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlContSCatSet', function(DlContSCatSet) {
                                return DlContSCatSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContSCatSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('dlContSCatSet.delete', {
                parent: 'dlContSCatSet',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlContSCatSet/dlContSCatSet-delete-dialog.html',
                        controller: 'DlContSCatSetDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlContSCatSet', function(DlContSCatSet) {
                                return DlContSCatSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContSCatSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
