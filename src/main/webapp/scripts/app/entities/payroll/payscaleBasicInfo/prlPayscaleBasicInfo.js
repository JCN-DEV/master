'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlPayscaleBasicInfo', {
                parent: 'payroll',
                url: '/prlPayscaleBasicInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlPayscaleBasicInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/payscaleBasicInfo/prlPayscaleBasicInfos.html',
                        controller: 'PrlPayscaleBasicInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlPayscaleBasicInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlPayscaleBasicInfo.detail', {
                parent: 'payroll',
                url: '/prlPayscaleBasicInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlPayscaleBasicInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/payscaleBasicInfo/prlPayscaleBasicInfo-detail.html',
                        controller: 'PrlPayscaleBasicInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlPayscaleBasicInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlPayscaleBasicInfo', function($stateParams, PrlPayscaleBasicInfo) {
                        return PrlPayscaleBasicInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlPayscaleBasicInfo.new', {
                parent: 'prlPayscaleBasicInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/payscaleBasicInfo/prlPayscaleBasicInfo-dialog.html',
                        controller: 'PrlPayscaleBasicInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlPayscaleBasicInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            serialNumber: null,
                            basicAmount: null,
                            activeStatus: true,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('prlPayscaleBasicInfo.edit', {
                parent: 'prlPayscaleBasicInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/payscaleBasicInfo/prlPayscaleBasicInfo-dialog.html',
                        controller: 'PrlPayscaleBasicInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlPayscaleBasicInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlPayscaleBasicInfo', function($stateParams, PrlPayscaleBasicInfo) {
                        return PrlPayscaleBasicInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlPayscaleBasicInfo.delete', {
                parent: 'prlPayscaleBasicInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/payscaleBasicInfo/prlPayscaleBasicInfo-delete-dialog.html',
                        controller: 'PrlPayscaleBasicInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlPayscaleBasicInfo', function(PrlPayscaleBasicInfo) {
                                return PrlPayscaleBasicInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlPayscaleBasicInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
