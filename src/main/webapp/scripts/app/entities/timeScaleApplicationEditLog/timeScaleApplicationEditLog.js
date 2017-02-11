'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('timeScaleApplicationEditLog', {
                parent: 'entity',
                url: '/timeScaleApplicationEditLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.timeScaleApplicationEditLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeScaleApplicationEditLog/timeScaleApplicationEditLogs.html',
                        controller: 'TimeScaleApplicationEditLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeScaleApplicationEditLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('timeScaleApplicationEditLog.detail', {
                parent: 'entity',
                url: '/timeScaleApplicationEditLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.timeScaleApplicationEditLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeScaleApplicationEditLog/timeScaleApplicationEditLog-detail.html',
                        controller: 'TimeScaleApplicationEditLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeScaleApplicationEditLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TimeScaleApplicationEditLog', function($stateParams, TimeScaleApplicationEditLog) {
                        return TimeScaleApplicationEditLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('timeScaleApplicationEditLog.new', {
                parent: 'timeScaleApplicationEditLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/timeScaleApplicationEditLog/timeScaleApplicationEditLog-dialog.html',
                        controller: 'TimeScaleApplicationEditLogDialogController',
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
                        $state.go('timeScaleApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('timeScaleApplicationEditLog');
                    })
                }]
            })
            .state('timeScaleApplicationEditLog.edit', {
                parent: 'timeScaleApplicationEditLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/timeScaleApplicationEditLog/timeScaleApplicationEditLog-dialog.html',
                        controller: 'TimeScaleApplicationEditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TimeScaleApplicationEditLog', function(TimeScaleApplicationEditLog) {
                                return TimeScaleApplicationEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeScaleApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('timeScaleApplicationEditLog.delete', {
                parent: 'timeScaleApplicationEditLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/timeScaleApplicationEditLog/timeScaleApplicationEditLog-delete-dialog.html',
                        controller: 'TimeScaleApplicationEditLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TimeScaleApplicationEditLog', function(TimeScaleApplicationEditLog) {
                                return TimeScaleApplicationEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeScaleApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
