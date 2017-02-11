'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almEmpLeaveTypeMap', {
                parent: 'alm',
                url: '/almEmpLeaveTypeMaps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveTypeMap.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveTypeMap/almEmpLeaveTypeMaps.html',
                        controller: 'AlmEmpLeaveTypeMapController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveTypeMap');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almEmpLeaveTypeMap.detail', {
                parent: 'alm',
                url: '/almEmpLeaveTypeMap/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveTypeMap.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveTypeMap/almEmpLeaveTypeMap-detail.html',
                        controller: 'AlmEmpLeaveTypeMapDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveTypeMap');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEmpLeaveTypeMap', function($stateParams, AlmEmpLeaveTypeMap) {
                        return AlmEmpLeaveTypeMap.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almEmpLeaveTypeMap.new', {
                parent: 'almEmpLeaveTypeMap',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveTypeMap/almEmpLeaveTypeMap-dialog.html',
                        controller: 'AlmEmpLeaveTypeMapDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveTypeMap');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            effectiveDate: null,
                            currentBalance: null,
                            newBalance: null,
                            isAddition: true,
                            reason: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('almEmpLeaveTypeMap.edit', {
                parent: 'almEmpLeaveTypeMap',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveTypeMap/almEmpLeaveTypeMap-dialog.html',
                        controller: 'AlmEmpLeaveTypeMapDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveTypeMap');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEmpLeaveTypeMap', function($stateParams, AlmEmpLeaveTypeMap) {
                        return AlmEmpLeaveTypeMap.get({id : $stateParams.id});
                    }]
                }
            });
    });
