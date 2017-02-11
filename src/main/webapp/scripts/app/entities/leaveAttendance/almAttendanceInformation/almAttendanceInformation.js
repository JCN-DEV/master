'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almAttendanceInformation', {
                parent: 'alm',
                url: '/almAttendanceInformations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almAttendanceInformation.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almAttendanceInformation/almAttendanceInformations.html',
                        controller: 'AlmAttendanceInformationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almAttendanceInformation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almAttendanceInformation.detail', {
                parent: 'alm',
                url: '/almAttendanceInformation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almAttendanceInformation.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almAttendanceInformation/almAttendanceInformation-detail.html',
                        controller: 'AlmAttendanceInformationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almAttendanceInformation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmAttendanceInformation', function($stateParams, AlmAttendanceInformation) {
                        return AlmAttendanceInformation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almAttendanceInformation.new', {
                parent: 'almAttendanceInformation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almAttendanceInformation/almAttendanceInformation-dialog.html',
                        controller: 'AlmAttendanceInformationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almAttendanceInformation');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            officeIn: null,
                            officeOut: null,
                            punchIn: null,
                            punchInNote: null,
                            punchOut: null,
                            punchOutNote: null,
                            processDate: null,
                            isProcessed: null,
                            activeStatus: false
                        };
                    }
                }
            })
            .state('almAttendanceInformation.edit', {
                parent: 'almAttendanceInformation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almAttendanceInformation/almAttendanceInformation-dialog.html',
                        controller: 'AlmAttendanceInformationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almAttendanceInformation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmAttendanceInformation', function($stateParams, AlmAttendanceInformation) {
                        return AlmAttendanceInformation.get({id : $stateParams.id});
                    }]
                }
            });
    });
