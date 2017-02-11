'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlSalaryAllowDeducInfo', {
                parent: 'payroll',
                url: '/prlSalaryAllowDeducInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlSalaryAllowDeducInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/salaryAllowDeducInfo/prlSalaryAllowDeducInfos.html',
                        controller: 'PrlSalaryAllowDeducInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlSalaryAllowDeducInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlSalaryAllowDeducInfo.detail', {
                parent: 'payroll',
                url: '/prlSalaryAllowDeducInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlSalaryAllowDeducInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/salaryAllowDeducInfo/prlSalaryAllowDeducInfo-detail.html',
                        controller: 'PrlSalaryAllowDeducInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlSalaryAllowDeducInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlSalaryAllowDeducInfo', function($stateParams, PrlSalaryAllowDeducInfo) {
                        return PrlSalaryAllowDeducInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlSalaryAllowDeducInfo.new', {
                parent: 'prlSalaryAllowDeducInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/salaryAllowDeducInfo/prlSalaryAllowDeducInfo-dialog.html',
                        controller: 'PrlSalaryAllowDeducInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlSalaryAllowDeducInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            allowDeducType: null,
                            allowDeducValue: null,
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
            .state('prlSalaryAllowDeducInfo.edit', {
                parent: 'prlSalaryAllowDeducInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/salaryAllowDeducInfo/prlSalaryAllowDeducInfo-dialog.html',
                        controller: 'PrlSalaryAllowDeducInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlSalaryAllowDeducInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlSalaryAllowDeducInfo', function($stateParams, PrlSalaryAllowDeducInfo) {
                        return PrlSalaryAllowDeducInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlSalaryAllowDeducInfo.delete', {
                parent: 'prlSalaryAllowDeducInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/salaryAllowDeducInfo/prlSalaryAllowDeducInfo-delete-dialog.html',
                        controller: 'PrlSalaryAllowDeducInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlSalaryAllowDeducInfo', function(PrlSalaryAllowDeducInfo) {
                                return PrlSalaryAllowDeducInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlSalaryAllowDeducInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
