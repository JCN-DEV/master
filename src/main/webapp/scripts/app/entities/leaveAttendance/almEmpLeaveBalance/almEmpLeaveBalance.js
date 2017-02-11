'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almEmpLeaveBalance', {
                parent: 'alm',
                url: '/almEmpLeaveBalances',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveBalance.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveBalance/almEmpLeaveBalances.html',
                        controller: 'AlmEmpLeaveBalanceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveBalance');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almEmpLeaveBalance.detail', {
                parent: 'alm',
                url: '/almEmpLeaveBalance/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveBalance.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveBalance/almEmpLeaveBalance-detail.html',
                        controller: 'AlmEmpLeaveBalanceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveBalance');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEmpLeaveBalance', function($stateParams, AlmEmpLeaveBalance) {
                        return AlmEmpLeaveBalance.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almEmpLeaveBalance.new', {
                parent: 'almEmpLeaveBalance',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveBalance/almEmpLeaveBalance-dialog.html',
                        controller: 'AlmEmpLeaveBalanceDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveBalance');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            yearOpenDate: null,
                            year: null,
                            leaveEarned: null,
                            leaveTaken: null,
                            leaveForwarded: null,
                            attendanceLeave: null,
                            leaveOnApply: null,
                            leaveBalance: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('almEmpLeaveBalance.edit', {
                parent: 'almEmpLeaveBalance',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveBalance/almEmpLeaveBalance-Dialog.html',
                        controller: 'AlmEmpLeaveBalanceDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveBalance');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEmpLeaveBalance', function($stateParams, AlmEmpLeaveBalance) {
                        return AlmEmpLeaveBalance.get({id : $stateParams.id});
                    }]
                }
            });
    });
