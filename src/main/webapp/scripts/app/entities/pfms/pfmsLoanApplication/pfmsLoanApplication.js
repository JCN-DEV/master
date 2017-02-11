'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pfmsLoanApplication', {
                parent: 'hrm',
                url: '/pfmsLoanApplications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsLoanApplication.home.title',
                    displayName: 'Loan Application'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsLoanApplication/pfmsLoanApplications.html',
                        controller: 'PfmsLoanApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsLoanApplication');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pfmsLoanApplication.detail', {
                parent: 'hrm',
                url: '/pfmsLoanApplication/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsLoanApplication.detail.title',
                    displayName: 'Loan Application Details'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsLoanApplication/pfmsLoanApplication-detail.html',
                        controller: 'PfmsLoanApplicationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsLoanApplication');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsLoanApplication', function($stateParams, PfmsLoanApplication) {
                        return PfmsLoanApplication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pfmsLoanApplication.new', {
                parent: 'pfmsLoanApplication',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Add Loan Application'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsLoanApplication/pfmsLoanApplication-dialog.html',
                        controller: 'PfmsLoanApplicationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsLoanApplication');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            timesOfWithdraw: 1,
                            loanAmount: null,
                            noOfInstallment: null,
                            reasonOfWithdrawal: null,
                            isRepayFirstWithdraw: true,
                            noOfInstallmentLeft: null,
                            lastInstallmentReDate: null,
                            applicationDate: null
                        };
                    }
                }
            })
            .state('pfmsLoanApplication.edit', {
                parent: 'pfmsLoanApplication',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Edit Loan Application'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsLoanApplication/pfmsLoanApplication-dialog.html',
                        controller: 'PfmsLoanApplicationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsLoanApplication');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsLoanApplication', function($stateParams, PfmsLoanApplication) {
                        //return PfmsLoanApplication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pfmsLoanAppPending', {
                parent: 'pfms',
                url: '/pfmsLoanApplicationPending',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsLoanApplication.home.title',
                    displayName: 'Loan Application'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsLoanApplication/pfmsPendinLoanApplication.html',
                        controller: 'PfmsLoanAppRequestLController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsLoanApplication');
                        $translatePartialLoader.addPart('approvalStatus');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('hrmHome');
                        return $translate.refresh();
                    }]
                }
            });
    });
