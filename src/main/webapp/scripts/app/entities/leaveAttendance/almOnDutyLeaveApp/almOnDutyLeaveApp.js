'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almOnDutyLeaveApp', {
                parent: 'hrm',
                url: '/almOnDutyLeaveApps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almOnDutyLeaveApp.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almOnDutyLeaveApp/almOnDutyLeaveApps.html',
                        controller: 'AlmOnDutyLeaveAppController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almOnDutyLeaveApp');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('almEmpLeaveApplication');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almEmpOnDutyLeaveAppPending', {
                parent: 'alm',
                url: '/almOnDutyLeaveApplicationPending',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almOnDutyLeaveApp.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almOnDutyLeaveApp/almEmpOnDutyLeaveAppPending.html',
                        controller: 'AlmOnDutyAppRequestLController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almOnDutyLeaveApp');
                        $translatePartialLoader.addPart('applicationLeaveStatuses');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('hrmHome');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almOnDutyLeaveApp.detail', {
                parent: 'alm',
                url: '/almOnDutyLeaveApp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almOnDutyLeaveApp.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almOnDutyLeaveApp/almOnDutyLeaveApp-detail.html',
                        controller: 'AlmOnDutyLeaveAppDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almOnDutyLeaveApp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmOnDutyLeaveApp', function($stateParams, AlmOnDutyLeaveApp) {
                        return AlmOnDutyLeaveApp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almOnDutyLeaveApp.new', {
                parent: 'almOnDutyLeaveApp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almOnDutyLeaveApp/almOnDutyLeaveApp-dialog.html',
                        controller: 'AlmOnDutyLeaveAppDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almOnDutyLeaveApp');
                        $translatePartialLoader.addPart('almEmpLeaveApplication');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            applicationDate: null,
                            dutyDate: null,
                            dutyInTimeHour: null,
                            dutyInTimeMin: null,
                            dutyOutTimeHour: null,
                            dutyOutTimeMin: null,
                            endDutyDate: null,
                            reason: null,
                            activeStatus: true,
                            isMoreDay: false
                        };
                    }
                }
            })
            .state('almOnDutyLeaveApp.edit', {
                parent: 'almOnDutyLeaveApp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almOnDutyLeaveApp/almOnDutyLeaveApp-dialog.html',
                        controller: 'AlmOnDutyLeaveAppDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almOnDutyLeaveApp');
                        $translatePartialLoader.addPart('almEmpLeaveApplication');

                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmOnDutyLeaveApp', function($stateParams, AlmOnDutyLeaveApp) {
                        return AlmOnDutyLeaveApp.get({id : $stateParams.id});
                    }]
                }
            }).state('hrm.ondutyleaveform', {
                parent: 'hrm',
                url: '/ondutyleaveform',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almOnDutyLeaveApp/almOnDutyLeaveApp-dialog.html',
                        controller: 'AlmOnDutyLeaveAppDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almOnDutyLeaveApp');
                        $translatePartialLoader.addPart('almEmpLeaveApplication');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            applicationDate: null,
                            dutyDate: null,
                            dutyInTimeHour: null,
                            dutyInTimeMin: null,
                            dutyOutTimeHour: null,
                            dutyOutTimeMin: null,
                            endDutyDate: null,
                            reason: null,
                            activeStatus: true,
                            isMoreDay: false
                        };
                    }
                }
            });
    });
