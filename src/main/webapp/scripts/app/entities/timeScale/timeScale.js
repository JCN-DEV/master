'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('timeScale', {
                parent: 'entity',
                url: '/timeScales',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.timeScale.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeScale/timeScales.html',
                        controller: 'TimeScaleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeScale');
                        $translatePartialLoader.addPart('timeScaleLevel');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('timeScale.detail', {
                parent: 'entity',
                url: '/timeScale/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.timeScale.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeScale/timeScale-detail.html',
                        controller: 'TimeScaleDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeScale');
                        $translatePartialLoader.addPart('timeScaleLevel');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TimeScale', function($stateParams, TimeScale) {
                        return TimeScale.get({id : $stateParams.id});
                    }]
                }
            })
            .state('timeScale.new', {
                parent: 'timeScale',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/timeScale/timeScale-dialog.html',
                        controller: 'TimeScaleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    level: null,
                                    classGrade: null,
                                    salaryAmount: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('timeScale', null, { reload: true });
                    }, function() {
                        $state.go('timeScale');
                    })
                }]
            })
            .state('timeScale.edit', {
                parent: 'timeScale',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/timeScale/timeScale-dialog.html',
                        controller: 'TimeScaleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TimeScale', function(TimeScale) {
                                return TimeScale.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeScale', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('timeScale.delete', {
                parent: 'timeScale',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/timeScale/timeScale-delete-dialog.html',
                        controller: 'TimeScaleDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TimeScale', function(TimeScale) {
                                return TimeScale.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeScale', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
