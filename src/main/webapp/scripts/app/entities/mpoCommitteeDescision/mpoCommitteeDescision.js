'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mpoCommitteeDescision', {
                parent: 'entity',
                url: '/mpoCommitteeDescisions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoCommitteeDescision.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mpoCommitteeDescision/mpoCommitteeDescisions.html',
                        controller: 'MpoCommitteeDescisionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteeDescision');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoCommitteeDescision.detail', {
                parent: 'entity',
                url: '/mpoCommitteeDescision/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoCommitteeDescision.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mpoCommitteeDescision/mpoCommitteeDescision-detail.html',
                        controller: 'MpoCommitteeDescisionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteeDescision');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoCommitteeDescision', function($stateParams, MpoCommitteeDescision) {
                        return MpoCommitteeDescision.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpoCommitteeDescision.new', {
                parent: 'mpoCommitteeDescision',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoCommitteeDescision/mpoCommitteeDescision-dialog.html',
                        controller: 'MpoCommitteeDescisionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    comments: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('mpoCommitteeDescision', null, { reload: true });
                    }, function() {
                        $state.go('mpoCommitteeDescision');
                    })
                }]
            })
            .state('mpoCommitteeDescision.edit', {
                parent: 'mpoCommitteeDescision',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoCommitteeDescision/mpoCommitteeDescision-dialog.html',
                        controller: 'MpoCommitteeDescisionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MpoCommitteeDescision', function(MpoCommitteeDescision) {
                                return MpoCommitteeDescision.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoCommitteeDescision', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('mpoCommitteeDescision.delete', {
                parent: 'mpoCommitteeDescision',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoCommitteeDescision/mpoCommitteeDescision-delete-dialog.html',
                        controller: 'MpoCommitteeDescisionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['MpoCommitteeDescision', function(MpoCommitteeDescision) {
                                return MpoCommitteeDescision.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoCommitteeDescision', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
