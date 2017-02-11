'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('auditLogHistory', {
                parent: 'entity',
                url: '/auditLogHistorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.auditLogHistory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/auditLogHistory/auditLogHistorys.html',
                        controller: 'AuditLogHistoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('auditLogHistory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('auditLogHistory.detail', {
                parent: 'entity',
                url: '/auditLogHistory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.auditLogHistory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/auditLogHistory/auditLogHistory-detail.html',
                        controller: 'AuditLogHistoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('auditLogHistory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AuditLogHistory', function($stateParams, AuditLogHistory) {
                        return AuditLogHistory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('auditLogHistory.new', {
                parent: 'auditLogHistory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/auditLogHistory/auditLogHistory-dialog.html',
                        controller: 'AuditLogHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    colName: null,
                                    valueBefore: null,
                                    valueAfter: null,
                                    status: null,
                                    createBy: null,
                                    createDate: null,
                                    updateBy: null,
                                    updateDate: null,
                                    remarks: null,
                                    userId: null,
                                    entityName: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('auditLogHistory', null, { reload: true });
                    }, function() {
                        $state.go('auditLogHistory');
                    })
                }]
            })
            .state('auditLogHistory.edit', {
                parent: 'auditLogHistory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/auditLogHistory/auditLogHistory-dialog.html',
                        controller: 'AuditLogHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AuditLogHistory', function(AuditLogHistory) {
                                return AuditLogHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('auditLogHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
