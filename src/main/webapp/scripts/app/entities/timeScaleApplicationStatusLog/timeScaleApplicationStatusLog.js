'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('timeScaleApplicationStatusLog', {
                parent: 'entity',
                url: '/timeScaleApplicationStatusLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.timeScaleApplicationStatusLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeScaleApplicationStatusLog/timeScaleApplicationStatusLogs.html',
                        controller: 'TimeScaleApplicationStatusLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeScaleApplicationStatusLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('timeScaleApplicationStatusLog.detail', {
                parent: 'entity',
                url: '/timeScaleApplicationStatusLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.timeScaleApplicationStatusLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeScaleApplicationStatusLog/timeScaleApplicationStatusLog-detail.html',
                        controller: 'TimeScaleApplicationStatusLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeScaleApplicationStatusLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TimeScaleApplicationStatusLog', function($stateParams, TimeScaleApplicationStatusLog) {
                        return TimeScaleApplicationStatusLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('timeScaleApplicationStatusLog.new', {
                parent: 'timeScaleApplicationStatusLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/timeScaleApplicationStatusLog/timeScaleApplicationStatusLog-dialog.html',
                        controller: 'TimeScaleApplicationStatusLogDialogController',
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
                        $state.go('timeScaleApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('timeScaleApplicationStatusLog');
                    })
                }]
            })
            .state('timeScaleApplicationStatusLog.edit', {
                parent: 'timeScaleApplicationStatusLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/timeScaleApplicationStatusLog/timeScaleApplicationStatusLog-dialog.html',
                        controller: 'TimeScaleApplicationStatusLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TimeScaleApplicationStatusLog', function(TimeScaleApplicationStatusLog) {
                                return TimeScaleApplicationStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeScaleApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('timeScaleApplicationStatusLog.delete', {
                parent: 'timeScaleApplicationStatusLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/timeScaleApplicationStatusLog/timeScaleApplicationStatusLog-delete-dialog.html',
                        controller: 'TimeScaleApplicationStatusLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TimeScaleApplicationStatusLog', function(TimeScaleApplicationStatusLog) {
                                return TimeScaleApplicationStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeScaleApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
