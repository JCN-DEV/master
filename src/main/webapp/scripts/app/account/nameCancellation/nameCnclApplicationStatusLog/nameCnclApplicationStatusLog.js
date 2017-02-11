'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('nameCnclApplicationStatusLog', {
                parent: 'entity',
                url: '/nameCnclApplicationStatusLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.nameCnclApplicationStatusLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/nameCnclApplicationStatusLog/nameCnclApplicationStatusLogs.html',
                        controller: 'NameCnclApplicationStatusLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('nameCnclApplicationStatusLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('nameCnclApplicationStatusLog.detail', {
                parent: 'entity',
                url: '/nameCnclApplicationStatusLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.nameCnclApplicationStatusLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/nameCnclApplicationStatusLog/nameCnclApplicationStatusLog-detail.html',
                        controller: 'NameCnclApplicationStatusLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('nameCnclApplicationStatusLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'NameCnclApplicationStatusLog', function($stateParams, NameCnclApplicationStatusLog) {
                        return NameCnclApplicationStatusLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('nameCnclApplicationStatusLog.new', {
                parent: 'nameCnclApplicationStatusLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/nameCnclApplicationStatusLog/nameCnclApplicationStatusLog-dialog.html',
                        controller: 'NameCnclApplicationStatusLogDialogController',
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
                        $state.go('nameCnclApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('nameCnclApplicationStatusLog');
                    })
                }]
            })
            .state('nameCnclApplicationStatusLog.edit', {
                parent: 'nameCnclApplicationStatusLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/nameCnclApplicationStatusLog/nameCnclApplicationStatusLog-dialog.html',
                        controller: 'NameCnclApplicationStatusLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['NameCnclApplicationStatusLog', function(NameCnclApplicationStatusLog) {
                                return NameCnclApplicationStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('nameCnclApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('nameCnclApplicationStatusLog.delete', {
                parent: 'nameCnclApplicationStatusLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/nameCnclApplicationStatusLog/nameCnclApplicationStatusLog-delete-dialog.html',
                        controller: 'NameCnclApplicationStatusLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['NameCnclApplicationStatusLog', function(NameCnclApplicationStatusLog) {
                                return NameCnclApplicationStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('nameCnclApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
