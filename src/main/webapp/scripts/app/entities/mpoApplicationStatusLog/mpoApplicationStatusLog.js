'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mpoApplicationStatusLog', {
                parent: 'entity',
                url: '/mpoApplicationStatusLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoApplicationStatusLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mpoApplicationStatusLog/mpoApplicationStatusLogs.html',
                        controller: 'MpoApplicationStatusLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoApplicationStatusLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoApplicationStatusLog.detail', {
                parent: 'entity',
                url: '/mpoApplicationStatusLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoApplicationStatusLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mpoApplicationStatusLog/mpoApplicationStatusLog-detail.html',
                        controller: 'MpoApplicationStatusLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoApplicationStatusLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoApplicationStatusLog', function($stateParams, MpoApplicationStatusLog) {
                        return MpoApplicationStatusLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpoApplicationStatusLog.new', {
                parent: 'mpoApplicationStatusLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoApplicationStatusLog/mpoApplicationStatusLog-dialog.html',
                        controller: 'MpoApplicationStatusLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    status: null,
                                    remarks: null,
                                    fromDate: null,
                                    toDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('mpoApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('mpoApplicationStatusLog');
                    })
                }]
            })
            .state('mpoApplicationStatusLog.edit', {
                parent: 'mpoApplicationStatusLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoApplicationStatusLog/mpoApplicationStatusLog-dialog.html',
                        controller: 'MpoApplicationStatusLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MpoApplicationStatusLog', function(MpoApplicationStatusLog) {
                                return MpoApplicationStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('mpoApplicationStatusLog.delete', {
                parent: 'mpoApplicationStatusLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoApplicationStatusLog/mpoApplicationStatusLog-delete-dialog.html',
                        controller: 'MpoApplicationStatusLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['MpoApplicationStatusLog', function(MpoApplicationStatusLog) {
                                return MpoApplicationStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
