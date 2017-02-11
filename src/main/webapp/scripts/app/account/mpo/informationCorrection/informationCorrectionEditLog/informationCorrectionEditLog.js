'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('informationCorrectionEditLog', {
                parent: 'entity',
                url: '/informationCorrectionEditLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.informationCorrectionEditLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/informationCorrectionEditLog/informationCorrectionEditLogs.html',
                        controller: 'InformationCorrectionEditLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('informationCorrectionEditLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('informationCorrectionEditLog.detail', {
                parent: 'entity',
                url: '/informationCorrectionEditLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.informationCorrectionEditLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/informationCorrectionEditLog/informationCorrectionEditLog-detail.html',
                        controller: 'InformationCorrectionEditLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('informationCorrectionEditLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InformationCorrectionEditLog', function($stateParams, InformationCorrectionEditLog) {
                        return InformationCorrectionEditLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('informationCorrectionEditLog.new', {
                parent: 'informationCorrectionEditLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/informationCorrectionEditLog/informationCorrectionEditLog-dialog.html',
                        controller: 'InformationCorrectionEditLogDialogController',
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
                        $state.go('informationCorrectionEditLog', null, { reload: true });
                    }, function() {
                        $state.go('informationCorrectionEditLog');
                    })
                }]
            })
            .state('informationCorrectionEditLog.edit', {
                parent: 'informationCorrectionEditLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/informationCorrectionEditLog/informationCorrectionEditLog-dialog.html',
                        controller: 'InformationCorrectionEditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InformationCorrectionEditLog', function(InformationCorrectionEditLog) {
                                return InformationCorrectionEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('informationCorrectionEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('informationCorrectionEditLog.delete', {
                parent: 'informationCorrectionEditLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/informationCorrectionEditLog/informationCorrectionEditLog-delete-dialog.html',
                        controller: 'InformationCorrectionEditLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InformationCorrectionEditLog', function(InformationCorrectionEditLog) {
                                return InformationCorrectionEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('informationCorrectionEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
