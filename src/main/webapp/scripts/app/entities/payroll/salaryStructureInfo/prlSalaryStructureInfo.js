'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlSalaryStructureInfo', {
                parent: 'payroll',
                url: '/prlSalaryStructureInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlSalaryStructureInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/salaryStructureInfo/prlSalaryStructureInfos.html',
                        controller: 'PrlSalaryStructureInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlSalaryStructureInfo');
                        $translatePartialLoader.addPart('prlSalaryAllowDeducInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlSalaryStructureInfo.detail', {
                parent: 'payroll',
                url: '/prlSalaryStructureInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlSalaryStructureInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/salaryStructureInfo/prlSalaryStructureInfo-detail.html',
                        controller: 'PrlSalaryStructureInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlSalaryStructureInfo');
                        $translatePartialLoader.addPart('prlSalaryAllowDeducInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlSalaryStructureInfo', function($stateParams, PrlSalaryStructureInfo) {
                        return PrlSalaryStructureInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlSalaryStructureInfo.new', {
                parent: 'prlSalaryStructureInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/salaryStructureInfo/prlSalaryStructureInfo-dialog.html',
                        controller: 'PrlSalaryStructureInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlSalaryStructureInfo');
                        $translatePartialLoader.addPart('prlSalaryAllowDeducInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            effectiveDate: null,
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
            .state('prlSalaryStructureInfo.edit', {
                parent: 'prlSalaryStructureInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/salaryStructureInfo/prlSalaryStructureInfo-dialog.html',
                        controller: 'PrlSalaryStructureInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlSalaryStructureInfo');
                        $translatePartialLoader.addPart('prlSalaryAllowDeducInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlSalaryStructureInfo', function($stateParams, PrlSalaryStructureInfo) {
                        return {
                            effectiveDate: null,
                            basicAmount: null,
                            activeStatus: true,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: $stateParams.id
                        };
                    }]
                }
            })
            .state('prlSalaryStructureInfo.delete', {
                parent: 'prlSalaryStructureInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/salaryStructureInfo/prlSalaryStructureInfo-delete-dialog.html',
                        controller: 'PrlSalaryStructureInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlSalaryStructureInfo', function(PrlSalaryStructureInfo) {
                                return PrlSalaryStructureInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlSalaryStructureInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
