'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almEmpLeaveApplication', {
                parent: 'hrm',
                url: '/almEmpLeaveApplications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveApplication.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveApplication/almEmpLeaveApplications.html',
                        controller: 'AlmEmpLeaveApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveApplication');
                        $translatePartialLoader.addPart('applicationLeaveStatuses');
                        $translatePartialLoader.addPart('halfDayInfos');
                        $translatePartialLoader.addPart('paymentTypes');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almEmpLeaveAppPending', {
                parent: 'alm',
                url: '/almLeaveApplicationPending',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveApplication.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveApplication/almEmpLeaveAppPending.html',
                        controller: 'AlmApplicationRequestLController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveApplication');
                        $translatePartialLoader.addPart('applicationLeaveStatuses');
                        $translatePartialLoader.addPart('halfDayInfos');
                        $translatePartialLoader.addPart('paymentTypes');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('hrmHome');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almEmpLeaveApplication.detail', {
                parent: 'hrm',
                url: '/almEmpLeaveApplication/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveApplication.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveApplication/almEmpLeaveApplication-detail.html',
                        controller: 'AlmEmpLeaveApplicationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveApplication');
                        $translatePartialLoader.addPart('applicationLeaveStatuses');
                        $translatePartialLoader.addPart('halfDayInfos');
                        $translatePartialLoader.addPart('paymentTypes');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEmpLeaveApplication', function($stateParams, AlmEmpLeaveApplication) {
                        return AlmEmpLeaveApplication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almEmpLeaveApplication.new', {
                parent: 'almEmpLeaveApplication',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveApplication/almEmpLeaveApplication-dialog.html',
                        controller: 'AlmEmpLeaveApplicationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveApplication');
                        $translatePartialLoader.addPart('applicationLeaveStatuses');
                        $translatePartialLoader.addPart('halfDayInfos');
                        $translatePartialLoader.addPart('paymentTypes');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            applicationDate: null,
                            applicationLeaveStatus: null,
                            leaveFromDate: null,
                            leaveToDate: null,
                            halfDayLeaveInfo: null,
                            isWithFinance: false,
                            isWithDdo: false,
                            isHalfDayLeave: false,
                            responsibleEmp: null,
                            addressWhileLeave: null,
                            emergencyContactNo: null,
                            reasonOfLeave: null,
                            contactNo: null,
                            isWithCertificate: false,
                            assignResposibilty: null,
                            paymentType: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('almEmpLeaveApplication.edit', {
                parent: 'almEmpLeaveApplication',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveApplication/almEmpLeaveApplication-dialog.html',
                        controller: 'AlmEmpLeaveApplicationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveApplication');
                        $translatePartialLoader.addPart('applicationLeaveStatuses');
                        $translatePartialLoader.addPart('halfDayInfos');
                        $translatePartialLoader.addPart('paymentTypes');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEmpLeaveApplication', function($stateParams, AlmEmpLeaveApplication) {
                        return AlmEmpLeaveApplication.get({id : $stateParams.id});
                    }]
                }
            }).state('hrm.leaveapplform', {
                parent: 'hrm',
                url: '/leaveapplform',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveApplication/almEmpLeaveApplication-dialog.html',
                        controller: 'AlmEmpLeaveApplicationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveApplication');
                        $translatePartialLoader.addPart('applicationLeaveStatuses');
                        $translatePartialLoader.addPart('halfDayInfos');
                        $translatePartialLoader.addPart('paymentTypes');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            applicationDate: null,
                            applicationLeaveStatus: null,
                            leaveFromDate: null,
                            leaveToDate: null,
                            isHalfDayLeave: null,
                            halfDayLeaveInfo: null,
                            reasonOfLeave: null,
                            contactNo: null,
                            isWithCertificate: null,
                            assignResposibilty: null,
                            paymentType: null,
                            activeStatus: true
                        };
                    }
                }
            });
    });
