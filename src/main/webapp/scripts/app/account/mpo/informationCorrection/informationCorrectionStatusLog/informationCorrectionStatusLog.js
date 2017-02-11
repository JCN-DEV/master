'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('informationCorrectionStatusLog', {
                parent: 'entity',
                url: '/informationCorrectionStatusLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.informationCorrectionStatusLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/informationCorrectionStatusLog/informationCorrectionStatusLogs.html',
                        controller: 'InformationCorrectionStatusLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('informationCorrectionStatusLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('informationCorrectionStatusLog.detail', {
                parent: 'entity',
                url: '/informationCorrectionStatusLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.informationCorrectionStatusLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/informationCorrectionStatusLog/informationCorrectionStatusLog-detail.html',
                        controller: 'InformationCorrectionStatusLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('informationCorrectionStatusLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InformationCorrectionStatusLog', function($stateParams, InformationCorrectionStatusLog) {
                        return InformationCorrectionStatusLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('informationCorrectionStatusLog.new', {
                parent: 'informationCorrectionStatusLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/informationCorrectionStatusLog/informationCorrectionStatusLog-dialog.html',
                        controller: 'InformationCorrectionStatusLogDialogController',
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
                        $state.go('informationCorrectionStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('informationCorrectionStatusLog');
                    })
                }]
            })
            .state('informationCorrectionStatusLog.edit', {
                parent: 'informationCorrectionStatusLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/informationCorrectionStatusLog/informationCorrectionStatusLog-dialog.html',
                        controller: 'InformationCorrectionStatusLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InformationCorrectionStatusLog', function(InformationCorrectionStatusLog) {
                                return InformationCorrectionStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('informationCorrectionStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('informationCorrectionStatusLog.delete', {
                parent: 'informationCorrectionStatusLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/informationCorrectionStatusLog/informationCorrectionStatusLog-delete-dialog.html',
                        controller: 'InformationCorrectionStatusLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InformationCorrectionStatusLog', function(InformationCorrectionStatusLog) {
                                return InformationCorrectionStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('informationCorrectionStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
