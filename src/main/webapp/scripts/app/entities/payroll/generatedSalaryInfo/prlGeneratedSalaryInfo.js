'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlGeneratedSalaryInfo', {
                parent: 'payroll',
                url: '/prlGeneratedSalaryInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlGeneratedSalaryInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/generatedSalaryInfo/prlGeneratedSalaryInfos.html',
                        controller: 'PrlGeneratedSalaryInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlGeneratedSalaryInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlGeneratedSalaryInfo.detail', {
                parent: 'payroll',
                url: '/prlGeneratedSalaryInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlGeneratedSalaryInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/generatedSalaryInfo/prlGeneratedSalaryInfo-detail.html',
                        controller: 'PrlGeneratedSalaryInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlGeneratedSalaryInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlGeneratedSalaryInfo', function($stateParams, PrlGeneratedSalaryInfo) {
                        return PrlGeneratedSalaryInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlGeneratedSalaryInfo.new', {
                parent: 'prlGeneratedSalaryInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/generatedSalaryInfo/prlGeneratedSalaryInfo-dialog.html',
                        controller: 'PrlGeneratedSalaryInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlGeneratedSalaryInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            yearName: null,
                            monthName: null,
                            salaryType: null,
                            processDate: null,
                            disburseStatus: 'N',
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('prlGeneratedSalaryInfo.edit', {
                parent: 'prlGeneratedSalaryInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/generatedSalaryInfo/prlGeneratedSalaryInfo-dialog.html',
                        controller: 'PrlGeneratedSalaryInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlGeneratedSalaryInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlGeneratedSalaryInfo', function($stateParams, PrlGeneratedSalaryInfo) {
                        return PrlGeneratedSalaryInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlGeneratedSalaryInfo.delete', {
                parent: 'prlGeneratedSalaryInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/generatedSalaryInfo/prlGeneratedSalaryInfo-delete-dialog.html',
                        controller: 'PrlGeneratedSalaryInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlGeneratedSalaryInfo', function(PrlGeneratedSalaryInfo) {
                                return PrlGeneratedSalaryInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlGeneratedSalaryInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
