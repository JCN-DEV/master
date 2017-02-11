'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('result', {
                parent: 'entity',
                url: '/results',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.result.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/result/results.html',
                        controller: 'ResultController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('result');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('result.detail', {
                parent: 'entity',
                url: '/result/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.result.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/result/result-detail.html',
                        controller: 'ResultDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('result');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Result', function($stateParams, Result) {
                        return Result.get({id : $stateParams.id});
                    }]
                }
            })
            .state('result.new', {
                parent: 'result',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/result/result-dialog.html',
                        controller: 'ResultDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    year: null,
                                    result: null,
                                    remarks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('result', null, { reload: true });
                    }, function() {
                        $state.go('result');
                    })
                }]
            })
            .state('result.edit', {
                parent: 'result',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/result/result-dialog.html',
                        controller: 'ResultDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Result', function(Result) {
                                return Result.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('result', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('result.delete', {
                parent: 'result',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/result/result-delete-dialog.html',
                        controller: 'ResultDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Result', function(Result) {
                                return Result.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('result', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
