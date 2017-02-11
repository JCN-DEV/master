'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almEarningFrequency', {
                parent: 'alm',
                url: '/almEarningFrequencys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEarningFrequency.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEarningFrequency/almEarningFrequencys.html',
                        controller: 'AlmEarningFrequencyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEarningFrequency');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almEarningFrequency.detail', {
                parent: 'alm',
                url: '/almEarningFrequency/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEarningFrequency.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEarningFrequency/almEarningFrequency-detail.html',
                        controller: 'AlmEarningFrequencyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEarningFrequency');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEarningFrequency', function($stateParams, AlmEarningFrequency) {
                        return AlmEarningFrequency.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almEarningFrequency.new', {
                parent: 'almEarningFrequency',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEarningFrequency/almEarningFrequency-dialog.html',
                        controller: 'AlmEarningFrequencyDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEarningFrequency');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            earningFrequencyName: null,
                            description: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('almEarningFrequency.edit', {
                parent: 'almEarningFrequency',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEarningFrequency/almEarningFrequency-dialog.html',
                        controller: 'AlmEarningFrequencyDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEarningFrequency');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEarningFrequency', function($stateParams, AlmEarningFrequency) {
                        return AlmEarningFrequency.get({id : $stateParams.id});
                    }]
                }
            });
    });
