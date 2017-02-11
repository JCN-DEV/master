'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bEdApplicationStatusLog', {
                parent: 'entity',
                url: '/bEdApplicationStatusLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.bEdApplicationStatusLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bEdApplicationStatusLog/bEdApplicationStatusLogs.html',
                        controller: 'BEdApplicationStatusLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bEdApplicationStatusLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bEdApplicationStatusLog.detail', {
                parent: 'entity',
                url: '/bEdApplicationStatusLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.bEdApplicationStatusLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bEdApplicationStatusLog/bEdApplicationStatusLog-detail.html',
                        controller: 'BEdApplicationStatusLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bEdApplicationStatusLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'BEdApplicationStatusLog', function($stateParams, BEdApplicationStatusLog) {
                        return BEdApplicationStatusLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bEdApplicationStatusLog.new', {
                parent: 'bEdApplicationStatusLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bEdApplicationStatusLog/bEdApplicationStatusLog-dialog.html',
                        controller: 'BEdApplicationStatusLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    status: null,
                                    remarks: null,
                                    fromDate: null,
                                    toDate: null,
                                    cause: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('bEdApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('bEdApplicationStatusLog');
                    })
                }]
            })
            .state('bEdApplicationStatusLog.edit', {
                parent: 'bEdApplicationStatusLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bEdApplicationStatusLog/bEdApplicationStatusLog-dialog.html',
                        controller: 'BEdApplicationStatusLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['BEdApplicationStatusLog', function(BEdApplicationStatusLog) {
                                return BEdApplicationStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bEdApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('bEdApplicationStatusLog.delete', {
                parent: 'bEdApplicationStatusLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bEdApplicationStatusLog/bEdApplicationStatusLog-delete-dialog.html',
                        controller: 'BEdApplicationStatusLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['BEdApplicationStatusLog', function(BEdApplicationStatusLog) {
                                return BEdApplicationStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bEdApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
