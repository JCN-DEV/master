'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employeeLoanInfo.employeeLoanMonthlyDeduction', {
                parent: 'employeeLoanInfo',
                url: '/employeeLoanMonthlyDeductions',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.employeeLoanMonthlyDeduction.home.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanMonthlyDeduction/employeeLoanMonthlyDeductions.html',
                        controller: 'EmployeeLoanMonthlyDeductionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanMonthlyDeduction');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeLoanInfo.employeeLoanMonthlyDeduction.detail', {
                parent: 'employeeLoanInfo.employeeLoanMonthlyDeduction',
                url: '/employeeLoanMonthlyDeduction/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.employeeLoanMonthlyDeduction.detail.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanMonthlyDeduction/employeeLoanMonthlyDeduction-detail.html',
                        controller: 'EmployeeLoanMonthlyDeductionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanMonthlyDeduction');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EmployeeLoanMonthlyDeduction', function($stateParams, EmployeeLoanMonthlyDeduction) {
                        return EmployeeLoanMonthlyDeduction.get({id : $stateParams.id});
                    }]
                }
            })
            .state('employeeLoanInfo.employeeLoanMonthlyDeduction.new', {
                parent: 'employeeLoanInfo.employeeLoanMonthlyDeduction',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanMonthlyDeduction/employeeLoanMonthlyDeduction-dialog.html',
                        controller: 'EmployeeLoanMonthlyDeductionDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                            return {
                                month: null,
                                year: null,
                                deductedAmount: null,
                                reason: null,
                                status: null,
                                createDate: null,
                                createBy: null,
                                updateDate: null,
                                updateBy: null,
                                id: null
                            };
                        }
                }
            })

            .state('employeeLoanInfo.employeeLoanMonthlyDeduction.edit', {
                parent: 'employeeLoanInfo.employeeLoanMonthlyDeduction',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanMonthlyDeduction/employeeLoanMonthlyDeduction-dialog.html',
                        controller: 'EmployeeLoanMonthlyDeductionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['EmployeeLoanMonthlyDeduction', function(EmployeeLoanMonthlyDeduction) {
                                return EmployeeLoanMonthlyDeduction.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employeeLoanMonthlyDeduction', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
