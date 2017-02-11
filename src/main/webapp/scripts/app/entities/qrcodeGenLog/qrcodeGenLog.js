'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('qrcodeGenLog', {
                parent: 'entity',
                url: '/qrcodeGenLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.qrcodeGenLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/qrcodeGenLog/qrcodeGenLogs.html',
                        controller: 'QrcodeGenLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('qrcodeGenLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('qrcodeGenLog.detail', {
                parent: 'entity',
                url: '/qrcodeGenLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.qrcodeGenLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/qrcodeGenLog/qrcodeGenLog-detail.html',
                        controller: 'QrcodeGenLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('qrcodeGenLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'QrcodeGenLog', function($stateParams, QrcodeGenLog) {
                        return QrcodeGenLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('qrcodeGenLog.new', {
                parent: 'qrcodeGenLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/qrcodeGenLog/qrcodeGenLog-dialog.html',
                        controller: 'QrcodeGenLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    refId: null,
                                    urlLink: null,
                                    qrCodeType: null,
                                    dataDesc: null,
                                    genCode: null,
                                    createBy: null,
                                    updateBy: null,
                                    appName: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('qrcodeGenLog', null, { reload: true });
                    }, function() {
                        $state.go('qrcodeGenLog');
                    })
                }]
            })
            .state('qrcodeGenLog.edit', {
                parent: 'qrcodeGenLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/qrcodeGenLog/qrcodeGenLog-dialog.html',
                        controller: 'QrcodeGenLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['QrcodeGenLog', function(QrcodeGenLog) {
                                return QrcodeGenLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('qrcodeGenLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('qrcodeGenLog.delete', {
                parent: 'qrcodeGenLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/qrcodeGenLog/qrcodeGenLog-delete-dialog.html',
                        controller: 'QrcodeGenLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['QrcodeGenLog', function(QrcodeGenLog) {
                                return QrcodeGenLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('qrcodeGenLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
