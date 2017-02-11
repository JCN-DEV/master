'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pfmsLoanSchedule', {
                parent: 'pfms',
                url: '/pfmsLoanSchedules',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsLoanSchedule.home.title',
                    displayName: 'PFMS Loan Schedules'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsLoanSchedule/pfmsLoanSchedules.html',
                        controller: 'PfmsLoanScheduleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsLoanSchedule');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pfmsLoanSchedule.detail', {
                parent: 'pfms',
                url: '/pfmsLoanSchedule/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsLoanSchedule.detail.title',
                    displayName: 'Loan Schedules Details'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsLoanSchedule/pfmsLoanSchedule-detail.html',
                        controller: 'PfmsLoanScheduleDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsLoanSchedule');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsLoanSchedule', function($stateParams, PfmsLoanSchedule) {
                        return PfmsLoanSchedule.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pfmsLoanSchedule.new', {
                parent: 'pfmsLoanSchedule',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'New Loan Schedules'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsLoanSchedule/pfmsLoanSchedule-dialog.html',
                        controller: 'PfmsLoanScheduleDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsLoanSchedule');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            loanYear: null,
                            loanMonth: null,
                            deductedAmount: null,
                            installmentNo: null,
                            totInstallNo: null,
                            totLoanAmount: null,
                            isRepay: false,
                            reason: null,
                            activeStatus: false,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('pfmsLoanSchedule.edit', {
                parent: 'pfmsLoanSchedule',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Edit Loan Schedules'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsLoanSchedule/pfmsLoanSchedule-dialog.html',
                        controller: 'PfmsLoanScheduleDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsLoanSchedule');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsLoanSchedule', function($stateParams, PfmsLoanSchedule) {
                        return PfmsLoanSchedule.get({id : $stateParams.id});
                    }]
                }
            });
    });
