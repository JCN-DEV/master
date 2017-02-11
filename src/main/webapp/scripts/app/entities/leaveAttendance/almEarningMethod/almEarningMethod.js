'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almEarningMethod', {
                parent: 'alm',
                url: '/almEarningMethods',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEarningMethod.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEarningMethod/almEarningMethods.html',
                        controller: 'AlmEarningMethodController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEarningMethod');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almEarningMethod.detail', {
                parent: 'alm',
                url: '/almEarningMethod/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEarningMethod.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEarningMethod/almEarningMethod-detail.html',
                        controller: 'AlmEarningMethodDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEarningMethod');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEarningMethod', function($stateParams, AlmEarningMethod) {
                        return AlmEarningMethod.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almEarningMethod.new', {
                parent: 'almEarningMethod',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEarningMethod/almEarningMethod-dialog.html',
                        controller: 'AlmEarningMethodDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEarningMethod');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            earningMethodName: null,
                            description: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('almEarningMethod.edit', {
                parent: 'almEarningMethod',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEarningMethod/almEarningMethod-dialog.html',
                        controller: 'AlmEarningMethodDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEarningMethod');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEarningMethod', function($stateParams, AlmEarningMethod) {
                        return AlmEarningMethod.get({id : $stateParams.id});
                    }]
                }
            });
    });
