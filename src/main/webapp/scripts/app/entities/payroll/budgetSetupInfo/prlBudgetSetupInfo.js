'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlBudgetSetupInfo', {
                parent: 'payroll',
                url: '/prlBudgetSetupInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlBudgetSetupInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/budgetSetupInfo/prlBudgetSetupInfos.html',
                        controller: 'PrlBudgetSetupInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlBudgetSetupInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlBudgetSetupInfo.detail', {
                parent: 'payroll',
                url: '/prlBudgetSetupInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlBudgetSetupInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/budgetSetupInfo/prlBudgetSetupInfo-detail.html',
                        controller: 'PrlBudgetSetupInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlBudgetSetupInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlBudgetSetupInfo', function($stateParams, PrlBudgetSetupInfo) {
                        return PrlBudgetSetupInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlBudgetSetupInfo.new', {
                parent: 'prlBudgetSetupInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/budgetSetupInfo/prlBudgetSetupInfo-dialog.html',
                        controller: 'PrlBudgetSetupInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlBudgetSetupInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            budgetType: null,
                            codeType: null,
                            budgetYear: null,
                            codeValue: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('prlBudgetSetupInfo.edit', {
                parent: 'prlBudgetSetupInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/budgetSetupInfo/prlBudgetSetupInfo-dialog.html',
                        controller: 'PrlBudgetSetupInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlBudgetSetupInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlBudgetSetupInfo', function($stateParams, PrlBudgetSetupInfo) {
                        return PrlBudgetSetupInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlBudgetSetupInfo.delete', {
                parent: 'prlBudgetSetupInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/budgetSetupInfo/prlBudgetSetupInfo-delete-dialog.html',
                        controller: 'PrlBudgetSetupInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlBudgetSetupInfo', function(PrlBudgetSetupInfo) {
                                return PrlBudgetSetupInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlBudgetSetupInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
