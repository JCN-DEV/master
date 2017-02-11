'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('aPScaleApplication', {
                parent: 'entity',
                url: '/aPScaleApplications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aPScaleApplication.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aPScaleApplication/aPScaleApplications.html',
                        controller: 'APScaleApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aPScaleApplication');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('aPScaleApplication.detail', {
                parent: 'entity',
                url: '/aPScaleApplication/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.aPScaleApplication.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aPScaleApplication/aPScaleApplication-detail.html',
                        controller: 'APScaleApplicationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aPScaleApplication');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'APScaleApplication', function($stateParams, APScaleApplication) {
                        return APScaleApplication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('aPScaleApplication.new', {
                parent: 'aPScaleApplication',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aPScaleApplication/aPScaleApplication-dialog.html',
                        controller: 'APScaleApplicationDialogController',
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
                        $state.go('aPScaleApplication', null, { reload: true });
                    }, function() {
                        $state.go('aPScaleApplication');
                    })
                }]
            })
            .state('aPScaleApplication.edit', {
                parent: 'aPScaleApplication',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aPScaleApplication/aPScaleApplication-dialog.html',
                        controller: 'APScaleApplicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['APScaleApplication', function(APScaleApplication) {
                                return APScaleApplication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aPScaleApplication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('aPScaleApplication.delete', {
                parent: 'aPScaleApplication',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aPScaleApplication/aPScaleApplication-delete-dialog.html',
                        controller: 'APScaleApplicationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['APScaleApplication', function(APScaleApplication) {
                                return APScaleApplication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aPScaleApplication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
