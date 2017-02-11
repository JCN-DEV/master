'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almEmpLeaveInitialize', {
                parent: 'alm',
                url: '/almEmpLeaveInitializes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveInitialize.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveInitialize/almEmpLeaveInitializes.html',
                        controller: 'AlmEmpLeaveInitializeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveInitialize');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almEmpLeaveInitialize.detail', {
                parent: 'alm',
                url: '/almEmpLeaveInitialize/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveInitialize.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveInitialize/almEmpLeaveInitialize-detail.html',
                        controller: 'AlmEmpLeaveInitializeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveInitialize');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEmpLeaveInitialize', function($stateParams, AlmEmpLeaveInitialize) {
                        return AlmEmpLeaveInitialize.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almEmpLeaveInitialize.new', {
                parent: 'almEmpLeaveInitialize',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveInitialize/almEmpLeaveInitialize-dialog.html',
                        controller: 'AlmEmpLeaveInitializeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveInitialize');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            effectiveDate: null,
                            year: new Date().getFullYear(),
                            leaveEarned: null,
                            leaveTaken: null,
                            leaveForwarded: null,
                            leaveOnApply: null,
                            leaveBalance: null,
                            activeStatus: true,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('almEmpLeaveInitialize.edit', {
                parent: 'almEmpLeaveInitialize',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveInitialize/almEmpLeaveInitialize-dialog.html',
                        controller: 'AlmEmpLeaveInitializeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveInitialize');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEmpLeaveInitialize', function($stateParams, AlmEmpLeaveInitialize) {
                        return AlmEmpLeaveInitialize.get({id : $stateParams.id});
                    }]
                }
            });
    });
