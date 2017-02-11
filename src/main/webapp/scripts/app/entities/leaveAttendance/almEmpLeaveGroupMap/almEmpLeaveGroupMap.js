'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almEmpLeaveGroupMap', {
                parent: 'alm',
                url: '/almEmpLeaveGroupMaps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveGroupMap.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveGroupMap/almEmpLeaveGroupMaps.html',
                        controller: 'AlmEmpLeaveGroupMapController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveGroupMap');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almEmpLeaveGroupMap.detail', {
                parent: 'alm',
                url: '/almEmpLeaveGroupMap/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almEmpLeaveGroupMap.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveGroupMap/almEmpLeaveGroupMap-detail.html',
                        controller: 'AlmEmpLeaveGroupMapDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveGroupMap');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEmpLeaveGroupMap', function($stateParams, AlmEmpLeaveGroupMap) {
                        return AlmEmpLeaveGroupMap.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almEmpLeaveGroupMap.new', {
                parent: 'almEmpLeaveGroupMap',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveGroupMap/almEmpLeaveGroupMap-dialog.html',
                        controller: 'AlmEmpLeaveGroupMapDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveGroupMap');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            effectiveDate: null,
                            reason: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('almEmpLeaveGroupMap.edit', {
                parent: 'almEmpLeaveGroupMap',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almEmpLeaveGroupMap/almEmpLeaveGroupMap-dialog.html',
                        controller: 'AlmEmpLeaveGroupMapDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almEmpLeaveGroupMap');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmEmpLeaveGroupMap', function($stateParams, AlmEmpLeaveGroupMap) {
                        return AlmEmpLeaveGroupMap.get({id : $stateParams.id});
                    }]
                }
            });
    });
