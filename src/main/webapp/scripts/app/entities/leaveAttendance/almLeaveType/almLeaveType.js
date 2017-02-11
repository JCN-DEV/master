'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almLeaveType', {
                parent: 'alm',
                url: '/almLeaveTypes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almLeaveType.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeaveType/almLeaveTypes.html',
                        controller: 'AlmLeaveTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeaveType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almLeaveType.detail', {
                parent: 'alm',
                url: '/almLeaveType/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almLeaveType.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeaveType/almLeaveType-detail.html',
                        controller: 'AlmLeaveTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeaveType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmLeaveType', function($stateParams, AlmLeaveType) {
                        return AlmLeaveType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almLeaveType.new', {
                parent: 'almLeaveType',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },

                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeaveType/almLeaveType-dialog.html',
                        controller: 'AlmLeaveTypeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeaveType');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            leaveTypeName: null,
                            description: null,
                            activeStatus: true

                        };
                    }
                }
            })
            .state('almLeaveType.edit', {
                parent: 'almLeaveType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeaveType/almLeaveType-dialog.html',
                        controller: 'AlmLeaveTypeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeaveType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmLeaveType', function($stateParams, AlmLeaveType) {
                        return AlmLeaveType.get({id : $stateParams.id});
                    }]
                }
            });
    });
