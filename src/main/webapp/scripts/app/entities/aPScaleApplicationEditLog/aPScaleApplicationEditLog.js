'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('aPScaleApplicationEditLog', {
                parent: 'entity',
                url: '/aPScaleApplicationEditLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aPScaleApplicationEditLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aPScaleApplicationEditLog/aPScaleApplicationEditLogs.html',
                        controller: 'APScaleApplicationEditLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aPScaleApplicationEditLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('aPScaleApplicationEditLog.detail', {
                parent: 'entity',
                url: '/aPScaleApplicationEditLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aPScaleApplicationEditLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aPScaleApplicationEditLog/aPScaleApplicationEditLog-detail.html',
                        controller: 'APScaleApplicationEditLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aPScaleApplicationEditLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'APScaleApplicationEditLog', function($stateParams, APScaleApplicationEditLog) {
                        return APScaleApplicationEditLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('aPScaleApplicationEditLog.new', {
                parent: 'aPScaleApplicationEditLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aPScaleApplicationEditLog/aPScaleApplicationEditLog-dialog.html',
                        controller: 'APScaleApplicationEditLogDialogController',
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
                        $state.go('aPScaleApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('aPScaleApplicationEditLog');
                    })
                }]
            })
            .state('aPScaleApplicationEditLog.edit', {
                parent: 'aPScaleApplicationEditLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aPScaleApplicationEditLog/aPScaleApplicationEditLog-dialog.html',
                        controller: 'APScaleApplicationEditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['APScaleApplicationEditLog', function(APScaleApplicationEditLog) {
                                return APScaleApplicationEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aPScaleApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('aPScaleApplicationEditLog.delete', {
                parent: 'aPScaleApplicationEditLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aPScaleApplicationEditLog/aPScaleApplicationEditLog-delete-dialog.html',
                        controller: 'APScaleApplicationEditLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['APScaleApplicationEditLog', function(APScaleApplicationEditLog) {
                                return APScaleApplicationEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aPScaleApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
