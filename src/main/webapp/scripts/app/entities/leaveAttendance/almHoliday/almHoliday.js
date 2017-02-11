'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almHoliday', {
                parent: 'alm',
                url: '/almHolidays',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almHoliday.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almHoliday/almHolidays.html',
                        controller: 'AlmHolidayController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almHoliday');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almHoliday.detail', {
                parent: 'alm',
                url: '/almHoliday/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almHoliday.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almHoliday/almHoliday-detail.html',
                        controller: 'AlmHolidayDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almHoliday');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmHoliday', function($stateParams, AlmHoliday) {
                        return AlmHoliday.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almHoliday.new', {
                parent: 'almHoliday',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almHoliday/almHoliday-dialog.html',
                        controller: 'AlmHolidayDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almHoliday');
                        $translatePartialLoader.addPart('religions');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            typeName: null,
                            religion: null,
                            occasion: null,
                            fromDate: null,
                            toDate: null,
                            activeStatus: true

                        };
                    }
                }
            })
            .state('almHoliday.edit', {
                parent: 'almHoliday',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almHoliday/almHoliday-dialog.html',
                        controller: 'AlmHolidayDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almHoliday');
                        $translatePartialLoader.addPart('religions');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmHoliday', function($stateParams, AlmHoliday) {
                        return AlmHoliday.get({id : $stateParams.id});
                    }]
                }
            });
    });
