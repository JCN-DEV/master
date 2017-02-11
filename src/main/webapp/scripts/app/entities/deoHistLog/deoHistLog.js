'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('deoHistLog', {
                parent: 'entity',
                url: '/deoHistLogs',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.deoHistLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deoHistLog/deoHistLogs.html',
                        controller: 'DeoHistLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deoHistLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('deoHistLog.detail', {
                parent: 'entity',
                url: '/deoHistLog/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.deoHistLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deoHistLog/deoHistLog-detail.html',
                        controller: 'DeoHistLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deoHistLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DeoHistLog', function($stateParams, DeoHistLog) {
                        return DeoHistLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('deoHistLog.new', {
                parent: 'deoHistLog',
                url: '/new',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/deoHistLog/deoHistLog-dialog.html',
                        controller: 'DeoHistLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    dateCrated: null,
                                    dateModified: null,
                                    status: null,
                                    activated: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('deoHistLog', null, { reload: true });
                    }, function() {
                        $state.go('deoHistLog');
                    })
                }]
            })
            .state('deoHistLog.edit', {
                parent: 'deoHistLog',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/deoHistLog/deoHistLog-dialog.html',
                        controller: 'DeoHistLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DeoHistLog', function(DeoHistLog) {
                                return DeoHistLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('deoHistLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('deoHistLog.delete', {
                parent: 'deoHistLog',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/deoHistLog/deoHistLog-delete-dialog.html',
                        controller: 'DeoHistLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DeoHistLog', function(DeoHistLog) {
                                return DeoHistLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('deoHistLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
