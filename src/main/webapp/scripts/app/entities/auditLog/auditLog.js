'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('auditLog', {
                parent: 'entity',
                url: '/auditLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.auditLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/auditLog/auditLogs.html',
                        controller: 'AuditLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('auditLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('auditLog.detail', {
                parent: 'entity',
                url: '/auditLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.auditLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/auditLog/auditLog-detail.html',
                        controller: 'AuditLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('auditLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AuditLog', function($stateParams, AuditLog) {
                        return AuditLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('auditLog.new', {
                parent: 'auditLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/auditLog/auditLog-dialog.html',
                        controller: 'AuditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    eventTime: null,
                                    event: null,
                                    eventType: null,
                                    status: null,
                                    createBy: null,
                                    createDate: null,
                                    updateBy: null,
                                    updateDate: null,
                                    remarks: null,
                                    userId: null,
                                    userIpAddress: null,
                                    userMacAddress: null,
                                    deviceName: null,
                                    statusAction: null,
                                    types: null,
                                    userBrowser: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('auditLog', null, { reload: true });
                    }, function() {
                        $state.go('auditLog');
                    })
                }]
            })
            .state('auditLog.edit', {
                parent: 'auditLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/auditLog/auditLog-dialog.html',
                        controller: 'AuditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AuditLog', function(AuditLog) {
                                return AuditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('auditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
