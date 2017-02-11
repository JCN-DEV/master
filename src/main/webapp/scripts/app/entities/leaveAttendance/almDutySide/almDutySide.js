'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almDutySide', {
                parent: 'alm',
                url: '/almDutySides',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almDutySide.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almDutySide/almDutySides.html',
                        controller: 'AlmDutySideController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almDutySide');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almDutySide.detail', {
                parent: 'alm',
                url: '/almDutySide/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almDutySide.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almDutySide/almDutySide-detail.html',
                        controller: 'AlmDutySideDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almDutySide');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmDutySide', function($stateParams, AlmDutySide) {
                        return AlmDutySide.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almDutySide.new', {
                parent: 'almDutySide',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almDutySide/almDutySide-dialog.html',
                        controller: 'AlmDutySideDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almDutySide');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            sideName: null,
                            description: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('almDutySide.edit', {
                parent: 'almDutySide',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almDutySide/almDutySide-dialog.html',
                        controller: 'AlmDutySideDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almDutySide');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmDutySide', function($stateParams, AlmDutySide) {
                        return AlmDutySide.get({id : $stateParams.id});
                    }]
                }
            });
    });
