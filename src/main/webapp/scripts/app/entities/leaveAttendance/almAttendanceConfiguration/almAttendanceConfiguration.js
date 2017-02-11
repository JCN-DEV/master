'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almAttendanceConfiguration', {
                parent: 'alm',
                url: '/almAttendanceConfigurations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almAttendanceConfiguration.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almAttendanceConfiguration/almAttendanceConfigurations.html',
                        controller: 'AlmAttendanceConfigurationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almAttendanceConfiguration');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almAttendanceConfiguration.detail', {
                parent: 'alm',
                url: '/almAttendanceConfiguration/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almAttendanceConfiguration.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almAttendanceConfiguration/almAttendanceConfiguration-detail.html',
                        controller: 'AlmAttendanceConfigurationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almAttendanceConfiguration');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmAttendanceConfiguration', function($stateParams, AlmAttendanceConfiguration) {
                        return AlmAttendanceConfiguration.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almAttendanceConfiguration.new', {
                parent: 'almAttendanceConfiguration',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almAttendanceConfiguration/almAttendanceConfiguration-dialog.html',
                        controller: 'AlmAttendanceConfigurationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almAttendanceConfiguration');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            employeeType: null,
                            effectedDateFrom: null,
                            effectedDateTo: null,
                            isUntilFurtherNotice: true,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('almAttendanceConfiguration.edit', {
                parent: 'almAttendanceConfiguration',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almAttendanceConfiguration/almAttendanceConfiguration-dialog.html',
                        controller: 'AlmAttendanceConfigurationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almAttendanceConfiguration');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmAttendanceConfiguration', function($stateParams, AlmAttendanceConfiguration) {
                        return AlmAttendanceConfiguration.get({id : $stateParams.id});
                    }]
                }
            });
    });
