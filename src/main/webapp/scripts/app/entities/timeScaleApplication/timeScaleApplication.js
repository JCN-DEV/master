'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('timeScaleApplication', {
                parent: 'entity',
                url: '/timeScaleApplications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.timeScaleApplication.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeScaleApplication/timeScaleApplications.html',
                        controller: 'TimeScaleApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeScaleApplication');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('timeScaleApplication.detail', {
                parent: 'entity',
                url: '/timeScaleApplication/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.timeScaleApplication.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeScaleApplication/timeScaleApplication-detail.html',
                        controller: 'TimeScaleApplicationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeScaleApplication');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TimeScaleApplication', function($stateParams, TimeScaleApplication) {
                        return TimeScaleApplication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('timeScaleApplication.new', {
                parent: 'timeScaleApplication',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/timeScaleApplication/timeScaleApplication-dialog.html',
                        controller: 'TimeScaleApplicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date: null,
                                    indexNo: null,
                                    status: null,
                                    resulationDate: null,
                                    agendaNumber: null,
                                    workingBreak: null,
                                    workingBreakStart: null,
                                    workingBreakEnd: null,
                                    disciplinaryAction: null,
                                    disActionCaseNo: null,
                                    disActionFileName: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('timeScaleApplication', null, { reload: true });
                    }, function() {
                        $state.go('timeScaleApplication');
                    })
                }]
            })
            .state('timeScaleApplication.edit', {
                parent: 'timeScaleApplication',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/timeScaleApplication/timeScaleApplication-dialog.html',
                        controller: 'TimeScaleApplicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TimeScaleApplication', function(TimeScaleApplication) {
                                return TimeScaleApplication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeScaleApplication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('timeScaleApplication.delete', {
                parent: 'timeScaleApplication',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/timeScaleApplication/timeScaleApplication-delete-dialog.html',
                        controller: 'TimeScaleApplicationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TimeScaleApplication', function(TimeScaleApplication) {
                                return TimeScaleApplication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeScaleApplication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
