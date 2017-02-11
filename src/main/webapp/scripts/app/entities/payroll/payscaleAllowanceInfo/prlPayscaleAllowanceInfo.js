'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlPayscaleAllowanceInfo', {
                parent: 'payroll',
                url: '/prlPayscaleAllowanceInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlPayscaleAllowanceInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/payscaleAllowanceInfo/prlPayscaleAllowanceInfos.html',
                        controller: 'PrlPayscaleAllowanceInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlPayscaleAllowanceInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlPayscaleAllowanceInfo.detail', {
                parent: 'payroll',
                url: '/prlPayscaleAllowanceInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlPayscaleAllowanceInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/payscaleAllowanceInfo/prlPayscaleAllowanceInfo-detail.html',
                        controller: 'PrlPayscaleAllowanceInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlPayscaleAllowanceInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlPayscaleAllowanceInfo', function($stateParams, PrlPayscaleAllowanceInfo) {
                        return PrlPayscaleAllowanceInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlPayscaleAllowanceInfo.new', {
                parent: 'prlPayscaleAllowanceInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/payscaleAllowanceInfo/prlPayscaleAllowanceInfo-dialog.html',
                        controller: 'PrlPayscaleAllowanceInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlPayscaleAllowanceInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            fixedBasic: null,
                            basicMinimum: null,
                            basicMaximum: null,
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
            .state('prlPayscaleAllowanceInfo.edit', {
                parent: 'prlPayscaleAllowanceInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/payscaleAllowanceInfo/prlPayscaleAllowanceInfo-dialog.html',
                        controller: 'PrlPayscaleAllowanceInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlPayscaleAllowanceInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlPayscaleAllowanceInfo', function($stateParams, PrlPayscaleAllowanceInfo) {
                        return PrlPayscaleAllowanceInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlPayscaleAllowanceInfo.delete', {
                parent: 'prlPayscaleAllowanceInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/payscaleAllowanceInfo/prlPayscaleAllowanceInfo-delete-dialog.html',
                        controller: 'PrlPayscaleAllowanceInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlPayscaleAllowanceInfo', function(PrlPayscaleAllowanceInfo) {
                                return PrlPayscaleAllowanceInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlPayscaleAllowanceInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
