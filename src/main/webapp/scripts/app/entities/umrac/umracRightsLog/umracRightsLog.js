'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('umracRightsLog', {
                parent: 'entity',
                url: '/umracRightsLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracRightsLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/umracRightsLog/umracRightsLogs.html',
                        controller: 'UmracRightsLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracRightsLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('umracRightsLog.detail', {
                parent: 'entity',
                url: '/umracRightsLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.umracRightsLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/umracRightsLog/umracRightsLog-detail.html',
                        controller: 'UmracRightsLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracRightsLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UmracRightsLog', function($stateParams, UmracRightsLog) {
                        return UmracRightsLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('umracRightsLog.new', {
                parent: 'umracRightsLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracRightsLog/umracRightsLog-dialog.html',
                        controller: 'UmracRightsLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    rightId: null,
                                    roleId: null,
                                    module_id: null,
                                    subModule_id: null,
                                    rights: null,
                                    description: null,
                                    status: true,
                                    changeDate: null,
                                    changeBy: null,
                                    updatedBy: null,
                                    updatedTime: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('umracRightsLog', null, { reload: true });
                    }, function() {
                        $state.go('umracRightsLog');
                    })
                }]
            })
            .state('umracRightsLog.edit', {
                parent: 'umracRightsLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/umracRightsLog/umracRightsLog-dialog.html',
                        controller: 'UmracRightsLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UmracRightsLog', function(UmracRightsLog) {
                                return UmracRightsLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('umracRightsLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
