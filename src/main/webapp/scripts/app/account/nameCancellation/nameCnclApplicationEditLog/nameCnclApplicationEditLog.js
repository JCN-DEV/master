'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('nameCnclApplicationEditLog', {
                parent: 'entity',
                url: '/nameCnclApplicationEditLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.nameCnclApplicationEditLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/nameCnclApplicationEditLog/nameCnclApplicationEditLogs.html',
                        controller: 'NameCnclApplicationEditLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('nameCnclApplicationEditLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('nameCnclApplicationEditLog.detail', {
                parent: 'entity',
                url: '/nameCnclApplicationEditLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.nameCnclApplicationEditLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/nameCnclApplicationEditLog/nameCnclApplicationEditLog-detail.html',
                        controller: 'NameCnclApplicationEditLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('nameCnclApplicationEditLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'NameCnclApplicationEditLog', function($stateParams, NameCnclApplicationEditLog) {
                        return NameCnclApplicationEditLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('nameCnclApplicationEditLog.new', {
                parent: 'nameCnclApplicationEditLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/nameCnclApplicationEditLog/nameCnclApplicationEditLog-dialog.html',
                        controller: 'NameCnclApplicationEditLogDialogController',
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
                        $state.go('nameCnclApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('nameCnclApplicationEditLog');
                    })
                }]
            })
            .state('nameCnclApplicationEditLog.edit', {
                parent: 'nameCnclApplicationEditLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/nameCnclApplicationEditLog/nameCnclApplicationEditLog-dialog.html',
                        controller: 'NameCnclApplicationEditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['NameCnclApplicationEditLog', function(NameCnclApplicationEditLog) {
                                return NameCnclApplicationEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('nameCnclApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('nameCnclApplicationEditLog.delete', {
                parent: 'nameCnclApplicationEditLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/nameCnclApplicationEditLog/nameCnclApplicationEditLog-delete-dialog.html',
                        controller: 'NameCnclApplicationEditLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['NameCnclApplicationEditLog', function(NameCnclApplicationEditLog) {
                                return NameCnclApplicationEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('nameCnclApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
