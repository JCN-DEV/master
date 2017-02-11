'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almWeekendConfiguration', {
                parent: 'alm',
                url: '/almWeekendConfigurations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almWeekendConfiguration.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almWeekendConfiguration/almWeekendConfigurations.html',
                        controller: 'AlmWeekendConfigurationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almWeekendConfiguration');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almWeekendConfiguration.detail', {
                parent: 'alm',
                url: '/almWeekendConfiguration/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almWeekendConfiguration.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almWeekendConfiguration/almWeekendConfiguration-detail.html',
                        controller: 'AlmWeekendConfigurationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almWeekendConfiguration');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmWeekendConfiguration', function($stateParams, AlmWeekendConfiguration) {
                        return AlmWeekendConfiguration.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almWeekendConfiguration.new', {
                parent: 'almWeekendConfiguration',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },

                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almWeekendConfiguration/almWeekendConfiguration-dialog.html',
                        controller: 'AlmWeekendConfigurationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almWeekendConfiguration');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            dayName: null,
                            isHalfDay: false,
                            halfDay: null,
                            description: null,
                            activeStatus: true
                        };
                    }
                }

            })
            .state('almWeekendConfiguration.edit', {
                parent: 'almWeekendConfiguration',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almWeekendConfiguration/almWeekendConfiguration-dialog.html',
                        controller: 'AlmWeekendConfigurationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almWeekendConfiguration');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmWeekendConfiguration', function($stateParams, AlmWeekendConfiguration) {
                        return AlmWeekendConfiguration.get({id : $stateParams.id});
                    }]
                }
            });
    });
