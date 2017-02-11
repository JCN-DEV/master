'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlSalaryGenerationLog', {
                parent: 'payroll',
                url: '/prlSalaryGenerationLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlSalaryGenerationLog.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/salaryGenerationLog/prlSalaryGenerationLogs.html',
                        controller: 'PrlSalaryGenerationLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlSalaryGenerationLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlSalaryGenerationLog.detail', {
                parent: 'payroll',
                url: '/prlSalaryGenerationLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlSalaryGenerationLog.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/salaryGenerationLog/prlSalaryGenerationLog-detail.html',
                        controller: 'PrlSalaryGenerationLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlSalaryGenerationLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlSalaryGenerationLog', function($stateParams, PrlSalaryGenerationLog) {
                        return PrlSalaryGenerationLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlSalaryGenerationLog.new', {
                parent: 'prlSalaryGenerationLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/salaryGenerationLog/prlSalaryGenerationLog-dialog.html',
                        controller: 'PrlSalaryGenerationLogDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlSalaryGenerationLog');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            generateDate: null,
                            comments: null,
                            actionStatus: null,
                            generateBy: null,
                            createDate: null,
                            createBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('prlSalaryGenerationLog.edit', {
                parent: 'prlSalaryGenerationLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/salaryGenerationLog/prlSalaryGenerationLog-dialog.html',
                        controller: 'PrlSalaryGenerationLogDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlSalaryGenerationLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlSalaryGenerationLog', function($stateParams, PrlSalaryGenerationLog) {
                        return PrlSalaryGenerationLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlSalaryGenerationLog.delete', {
                parent: 'prlSalaryGenerationLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/salaryGenerationLog/prlSalaryGenerationLog-delete-dialog.html',
                        controller: 'PrlSalaryGenerationLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlSalaryGenerationLog', function(PrlSalaryGenerationLog) {
                                return PrlSalaryGenerationLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlSalaryGenerationLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
