'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almAttendanceStatus', {
                parent: 'alm',
                url: '/almAttendanceStatuss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almAttendanceStatus.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almAttendanceStatus/almAttendanceStatuss.html',
                        controller: 'AlmAttendanceStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almAttendanceStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almAttendanceStatus.detail', {
                parent: 'alm',
                url: '/almAttendanceStatus/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almAttendanceStatus.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almAttendanceStatus/almAttendanceStatus-detail.html',
                        controller: 'AlmAttendanceStatusDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almAttendanceStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmAttendanceStatus', function($stateParams, AlmAttendanceStatus) {
                        return AlmAttendanceStatus.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almAttendanceStatus.new', {
                parent: 'almAttendanceStatus',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },

                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almAttendanceStatus/almAttendanceStatus-dialog.html',
                        controller: 'AlmAttendanceStatusDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almAttendanceStatus');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            attendanceStatusName: null,
                            description: null,
                            attendanceCode: null,
                            ShortCode: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('almAttendanceStatus.edit', {
                parent: 'almAttendanceStatus',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almAttendanceStatus/almAttendanceStatus-dialog.html',
                        controller: 'AlmAttendanceStatusDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almAttendanceStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmAttendanceStatus', function($stateParams, AlmAttendanceStatus) {
                        return AlmAttendanceStatus.get({id : $stateParams.id});
                    }]
                }
            });
    });
