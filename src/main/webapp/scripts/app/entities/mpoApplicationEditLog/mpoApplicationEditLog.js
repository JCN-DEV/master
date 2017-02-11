'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mpoApplicationEditLog', {
                parent: 'entity',
                url: '/mpoApplicationEditLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoApplicationEditLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mpoApplicationEditLog/mpoApplicationEditLogs.html',
                        controller: 'MpoApplicationEditLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoApplicationEditLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoApplicationEditLog.detail', {
                parent: 'entity',
                url: '/mpoApplicationEditLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoApplicationEditLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mpoApplicationEditLog/mpoApplicationEditLog-detail.html',
                        controller: 'MpoApplicationEditLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoApplicationEditLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoApplicationEditLog', function($stateParams, MpoApplicationEditLog) {
                        return MpoApplicationEditLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpoApplicationEditLog.new', {
                parent: 'mpoApplicationEditLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoApplicationEditLog/mpoApplicationEditLog-dialog.html',
                        controller: 'MpoApplicationEditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    status: null,
                                    remarks: null,
                                    date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('mpoApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('mpoApplicationEditLog');
                    })
                }]
            })
            .state('mpoApplicationEditLog.edit', {
                parent: 'mpoApplicationEditLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoApplicationEditLog/mpoApplicationEditLog-dialog.html',
                        controller: 'MpoApplicationEditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MpoApplicationEditLog', function(MpoApplicationEditLog) {
                                return MpoApplicationEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('mpoApplicationEditLog.delete', {
                parent: 'mpoApplicationEditLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoApplicationEditLog/mpoApplicationEditLog-delete-dialog.html',
                        controller: 'MpoApplicationEditLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['MpoApplicationEditLog', function(MpoApplicationEditLog) {
                                return MpoApplicationEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
