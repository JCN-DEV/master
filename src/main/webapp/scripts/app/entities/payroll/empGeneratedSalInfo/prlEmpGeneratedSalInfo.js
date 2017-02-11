'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlEmpGeneratedSalInfo', {
                parent: 'payroll',
                url: '/prlEmpGeneratedSalInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlEmpGeneratedSalInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/empGeneratedSalInfo/prlEmpGeneratedSalInfos.html',
                        controller: 'PrlEmpGeneratedSalInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEmpGeneratedSalInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlEmpGeneratedSalInfo.detail', {
                parent: 'payroll',
                url: '/prlEmpGeneratedSalInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlEmpGeneratedSalInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/empGeneratedSalInfo/prlEmpGeneratedSalInfo-detail.html',
                        controller: 'PrlEmpGeneratedSalInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEmpGeneratedSalInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlEmpGeneratedSalInfo', function($stateParams, PrlEmpGeneratedSalInfo) {
                        return PrlEmpGeneratedSalInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlEmpGeneratedSalInfo.new', {
                parent: 'prlEmpGeneratedSalInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/empGeneratedSalInfo/prlEmpGeneratedSalInfo-dialog.html',
                        controller: 'PrlEmpGeneratedSalInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEmpGeneratedSalInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            basicAmount: null,
                            grossAmount: null,
                            payableAmount: null,
                            disburseStatus:'N',
                            isDisbursable:'Y',
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('prlGeneratedSalaryProfile', {
                parent: 'hrm',
                url: '/generatedSalary',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/payroll/empGeneratedSalInfo/prlEmpGeneratedSalInfo-profile.html',
                        controller: 'PrlEmpGeneratedSalInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEmpGeneratedSalInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            basicAmount: null,
                            grossAmount: null,
                            payableAmount: null,
                            disburseStatus:'N',
                            isDisbursable:'Y',
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('prlEmpGeneratedSalInfo.edit', {
                parent: 'prlEmpGeneratedSalInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/empGeneratedSalInfo/prlEmpGeneratedSalInfo-dialog.html',
                        controller: 'PrlEmpGeneratedSalInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEmpGeneratedSalInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlEmpGeneratedSalInfo', function($stateParams, PrlEmpGeneratedSalInfo) {
                        return PrlEmpGeneratedSalInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlEmpGeneratedSalInfo.delete', {
                parent: 'prlEmpGeneratedSalInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/empGeneratedSalInfo/prlEmpGeneratedSalInfo-delete-dialog.html',
                        controller: 'PrlEmpGeneratedSalInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlEmpGeneratedSalInfo', function(PrlEmpGeneratedSalInfo) {
                                return PrlEmpGeneratedSalInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlEmpGeneratedSalInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
