'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almEmpLeaveCancellation', {
                parent: 'hrm',
                url: '/almEmpLeaveCancellations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveCancellation.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveCancellation/almEmpLeaveCancellations.html',
                        controller: 'AlmEmpLeaveCancellationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveCancellation');
                        $translatePartialLoader.addPart('cancelStatuses');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almEmpLeaveCanAppPending', {
                parent: 'alm',
                url: '/almLeaveCanApplicationPending',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveCancellation.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveCancellation/almPendingLeaveCacellation.html',
                        controller: 'AlmAppCancelRequestLController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveCancellation');
                        $translatePartialLoader.addPart('cancelStatuses');
                        $translatePartialLoader.addPart('halfDayInfos');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almEmpLeaveCancellation.detail', {
                parent: 'alm',
                url: '/almEmpLeaveCancellation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveCancellation.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveCancellation/almEmpLeaveCancellation-detail.html',
                        controller: 'AlmEmpLeaveCancellationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveCancellation');
                        $translatePartialLoader.addPart('cancelStatuses');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEmpLeaveCancellation', function($stateParams, AlmEmpLeaveCancellation) {
                        return AlmEmpLeaveCancellation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almEmpLeaveCancellation.new', {
                parent: 'almEmpLeaveCancellation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },

                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveCancellation/almEmpLeaveCancellation-dialog.html',
                        controller: 'AlmEmpLeaveCancellationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveCancellation');
                        $translatePartialLoader.addPart('cancelStatuses');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            requestDate: null,
                            requestType: null,
                            cancelStatus: null,
                            causeOfCancellation: null,
                            activeStatus: true
                        };
                    }
                }

            })
            .state('almEmpLeaveCancellation.edit', {
                parent: 'almEmpLeaveCancellation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveCancellation/almEmpLeaveCancellation-dialog.html',
                        controller: 'AlmEmpLeaveCancellationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveCancellation');
                        $translatePartialLoader.addPart('cancelStatuses');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEmpLeaveCancellation', function($stateParams, AlmEmpLeaveCancellation) {
                        return AlmEmpLeaveCancellation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrm.leavecanform', {
                parent: 'hrm',
                url: '/leavecanform',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveCancellation/almEmpLeaveCancellation-dialog.html',
                        controller: 'AlmEmpLeaveCancellationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveCancellation');
                        $translatePartialLoader.addPart('cancelStatuses');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            requestDate: null,
                            requestType: null,
                            cancelStatus: null,
                            causeOfCancellation: null,
                            activeStatus: true
                        };
                    }
                }
            });
    });
