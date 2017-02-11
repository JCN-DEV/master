'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlPayscaleInfo', {
                parent: 'payroll',
                url: '/prlPayscaleInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlPayscaleInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/payscaleInfo/prlPayscaleInfos.html',
                        controller: 'PrlPayscaleInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlPayscaleInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlPayscaleInfo.detail', {
                parent: 'payroll',
                url: '/prlPayscaleInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlPayscaleInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/payscaleInfo/prlPayscaleInfo-detail.html',
                        controller: 'PrlPayscaleInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlPayscaleInfo');
                        $translatePartialLoader.addPart('prlPayscaleBasicInfo');
                        $translatePartialLoader.addPart('prlPayscaleAllowanceInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlPayscaleInfo', function($stateParams, PrlPayscaleInfo) {
                        return PrlPayscaleInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlPayscaleInfo.new', {
                parent: 'prlPayscaleInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/payscaleInfo/prlPayscaleInfo-dialog.html',
                        controller: 'PrlPayscaleInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlPayscaleInfo');
                        $translatePartialLoader.addPart('prlPayscaleBasicInfo');
                        $translatePartialLoader.addPart('prlPayscaleAllowanceInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            name: null,
                            maxBasicElegYear: null,
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
            .state('prlPayscaleInfo.edit', {
                parent: 'prlPayscaleInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/payscaleInfo/prlPayscaleInfo-dialog.html',
                        controller: 'PrlPayscaleInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlPayscaleInfo');
                        $translatePartialLoader.addPart('prlPayscaleBasicInfo');
                        $translatePartialLoader.addPart('prlPayscaleAllowanceInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlPayscaleInfo', function($stateParams, PrlPayscaleInfo) {
                        return PrlPayscaleInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlPayscaleInfo.delete', {
                parent: 'prlPayscaleInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/payscaleInfo/prlPayscaleInfo-delete-dialog.html',
                        controller: 'PrlPayscaleInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlPayscaleInfo', function(PrlPayscaleInfo) {
                                return PrlPayscaleInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlPayscaleInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
