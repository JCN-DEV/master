'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('aPScaleApplicationStatusLog', {
                parent: 'entity',
                url: '/aPScaleApplicationStatusLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aPScaleApplicationStatusLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aPScaleApplicationStatusLog/aPScaleApplicationStatusLogs.html',
                        controller: 'APScaleApplicationStatusLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aPScaleApplicationStatusLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('aPScaleApplicationStatusLog.detail', {
                parent: 'entity',
                url: '/aPScaleApplicationStatusLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aPScaleApplicationStatusLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aPScaleApplicationStatusLog/aPScaleApplicationStatusLog-detail.html',
                        controller: 'APScaleApplicationStatusLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aPScaleApplicationStatusLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'APScaleApplicationStatusLog', function($stateParams, APScaleApplicationStatusLog) {
                        return APScaleApplicationStatusLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('aPScaleApplicationStatusLog.new', {
                parent: 'aPScaleApplicationStatusLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aPScaleApplicationStatusLog/aPScaleApplicationStatusLog-dialog.html',
                        controller: 'APScaleApplicationStatusLogDialogController',
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
                        $state.go('aPScaleApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('aPScaleApplicationStatusLog');
                    })
                }]
            })
            .state('aPScaleApplicationStatusLog.edit', {
                parent: 'aPScaleApplicationStatusLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aPScaleApplicationStatusLog/aPScaleApplicationStatusLog-dialog.html',
                        controller: 'APScaleApplicationStatusLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['APScaleApplicationStatusLog', function(APScaleApplicationStatusLog) {
                                return APScaleApplicationStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aPScaleApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('aPScaleApplicationStatusLog.delete', {
                parent: 'aPScaleApplicationStatusLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aPScaleApplicationStatusLog/aPScaleApplicationStatusLog-delete-dialog.html',
                        controller: 'APScaleApplicationStatusLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['APScaleApplicationStatusLog', function(APScaleApplicationStatusLog) {
                                return APScaleApplicationStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aPScaleApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
