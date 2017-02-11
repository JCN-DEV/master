'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almLeaveGroup', {
                parent: 'alm',
                url: '/almLeaveGroups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almLeaveGroup.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeaveGroup/almLeaveGroups.html',
                        controller: 'AlmLeaveGroupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeaveGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almLeaveGroup.detail', {
                parent: 'alm',
                url: '/almLeaveGroup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almLeaveGroup.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeaveGroup/almLeaveGroup-detail.html',
                        controller: 'AlmLeaveGroupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeaveGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmLeaveGroup', function($stateParams, AlmLeaveGroup) {
                        return AlmLeaveGroup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almLeaveGroup.new', {
                parent: 'almLeaveGroup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeaveGroup/almLeaveGroup-dialog.html',
                        controller: 'AlmLeaveGroupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeaveGroup');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            leaveGroupName: null,
                            description: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('almLeaveGroup.edit', {
                parent: 'almLeaveGroup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },

                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeaveGroup/almLeaveGroup-dialog.html',
                        controller: 'AlmLeaveGroupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeaveGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmLeaveGroup', function($stateParams, AlmLeaveGroup) {
                        return AlmLeaveGroup.get({id : $stateParams.id});
                    }]
                }
            });
    });
