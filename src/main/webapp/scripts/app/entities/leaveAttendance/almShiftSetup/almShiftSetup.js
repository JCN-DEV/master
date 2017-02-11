'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almShiftSetup', {
                parent: 'alm',
                url: '/almShiftSetups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almShiftSetup.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almShiftSetup/almShiftSetups.html',
                        controller: 'AlmShiftSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almShiftSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almShiftSetup.detail', {
                parent: 'alm',
                url: '/almShiftSetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almShiftSetup.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almShiftSetup/almShiftSetup-detail.html',
                        controller: 'AlmShiftSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almShiftSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmShiftSetup', function($stateParams, AlmShiftSetup) {
                        return AlmShiftSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almShiftSetup.new', {
                parent: 'almShiftSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },

                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almShiftSetup/almShiftSetup-dialog.html',
                        controller: 'AlmShiftSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almShiftSetup');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            shiftName: null,
                            description: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('almShiftSetup.edit', {
                parent: 'almShiftSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },

                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almShiftSetup/almShiftSetup-dialog.html',
                        controller: 'AlmShiftSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almShiftSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmShiftSetup', function($stateParams, AlmShiftSetup) {
                        return AlmShiftSetup.get({id : $stateParams.id});
                    }]
                }
            });
    });
