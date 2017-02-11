'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almLeavGrpTypeMap', {
                parent: 'alm',
                url: '/almLeavGrpTypeMaps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almLeavGrpTypeMap.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeavGrpTypeMap/almLeavGrpTypeMaps.html',
                        controller: 'AlmLeavGrpTypeMapController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeavGrpTypeMap');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almLeavGrpTypeMap.detail', {
                parent: 'alm',
                url: '/almLeavGrpTypeMap/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almLeavGrpTypeMap.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeavGrpTypeMap/almLeavGrpTypeMap-detail.html',
                        controller: 'AlmLeavGrpTypeMapDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeavGrpTypeMap');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmLeavGrpTypeMap', function($stateParams, AlmLeavGrpTypeMap) {
                        return AlmLeavGrpTypeMap.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almLeavGrpTypeMap.new', {
                parent: 'almLeavGrpTypeMap',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeavGrpTypeMap/almLeavGrpTypeMap-dialog.html',
                        controller: 'AlmLeavGrpTypeMapDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeavGrpTypeMap');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            reason: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('almLeavGrpTypeMap.edit', {
                parent: 'almLeavGrpTypeMap',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeavGrpTypeMap/almLeavGrpTypeMap-dialog.html',
                        controller: 'AlmLeavGrpTypeMapDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeavGrpTypeMap');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmLeavGrpTypeMap', function($stateParams, AlmLeavGrpTypeMap) {
                        return AlmLeavGrpTypeMap.get({id : $stateParams.id});
                    }]
                }
            });
    });
