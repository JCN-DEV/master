'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bEdApplicationEditLog', {
                parent: 'entity',
                url: '/bEdApplicationEditLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.bEdApplicationEditLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bEdApplicationEditLog/bEdApplicationEditLogs.html',
                        controller: 'BEdApplicationEditLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bEdApplicationEditLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bEdApplicationEditLog.detail', {
                parent: 'entity',
                url: '/bEdApplicationEditLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.bEdApplicationEditLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bEdApplicationEditLog/bEdApplicationEditLog-detail.html',
                        controller: 'BEdApplicationEditLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bEdApplicationEditLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'BEdApplicationEditLog', function($stateParams, BEdApplicationEditLog) {
                        return BEdApplicationEditLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bEdApplicationEditLog.new', {
                parent: 'bEdApplicationEditLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bEdApplicationEditLog/bEdApplicationEditLog-dialog.html',
                        controller: 'BEdApplicationEditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    status: null,
                                    remarks: null,
                                    created_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('bEdApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('bEdApplicationEditLog');
                    })
                }]
            })
            .state('bEdApplicationEditLog.edit', {
                parent: 'bEdApplicationEditLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bEdApplicationEditLog/bEdApplicationEditLog-dialog.html',
                        controller: 'BEdApplicationEditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['BEdApplicationEditLog', function(BEdApplicationEditLog) {
                                return BEdApplicationEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bEdApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('bEdApplicationEditLog.delete', {
                parent: 'bEdApplicationEditLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bEdApplicationEditLog/bEdApplicationEditLog-delete-dialog.html',
                        controller: 'BEdApplicationEditLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['BEdApplicationEditLog', function(BEdApplicationEditLog) {
                                return BEdApplicationEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bEdApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
