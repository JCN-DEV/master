'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almWorkingUnit', {
                parent: 'alm',
                url: '/almWorkingUnits',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almWorkingUnit.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almWorkingUnit/almWorkingUnits.html',
                        controller: 'AlmWorkingUnitController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almWorkingUnit');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almWorkingUnit.detail', {
                parent: 'alm',
                url: '/almWorkingUnit/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almWorkingUnit.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almWorkingUnit/almWorkingUnit-detail.html',
                        controller: 'AlmWorkingUnitDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almWorkingUnit');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmWorkingUnit', function($stateParams, AlmWorkingUnit) {
                        return AlmWorkingUnit.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almWorkingUnit.new', {
                parent: 'almWorkingUnit',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almWorkingUnit/almWorkingUnit-dialog.html',
                        controller: 'AlmWorkingUnitDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almWorkingUnit');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            officeStart: null,
                            delayTime: null,
                            absentTime: null,
                            officeEnd: null,
                            effectiveDate: null,
                            isWeekend: false,
                            isHalfDay: false,
                            activeStatus: true
                        };
                    }
                }

            })
            .state('almWorkingUnit.edit', {
                parent: 'almWorkingUnit',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },

                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almWorkingUnit/almWorkingUnit-dialog.html',
                        controller: 'AlmWorkingUnitDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almWorkingUnit');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmWorkingUnit', function($stateParams, AlmWorkingUnit) {
                        return AlmWorkingUnit.get({id : $stateParams.id});
                    }]
                }
            });
    });
