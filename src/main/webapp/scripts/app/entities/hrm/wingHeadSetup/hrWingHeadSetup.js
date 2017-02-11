'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrWingHeadSetup', {
                parent: 'hrm',
                url: '/hrWingHeadSetups',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrWingHeadSetup.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/wingHeadSetup/hrWingHeadSetups.html',
                        controller: 'HrWingHeadSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrWingHeadSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrWingHeadSetup.detail', {
                parent: 'hrm',
                url: '/hrWingHeadSetup/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrWingHeadSetup.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/wingHeadSetup/hrWingHeadSetup-detail.html',
                        controller: 'HrWingHeadSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrWingHeadSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrWingHeadSetup', function($stateParams, HrWingHeadSetup) {
                        return HrWingHeadSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrWingHeadSetup.new', {
                parent: 'hrWingHeadSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/wingHeadSetup/hrWingHeadSetup-dialog.html',
                        controller: 'HrWingHeadSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrWingHeadSetup');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            joinDate: null,
                            endDate: null,
                            activeHead: false,
                            activeStatus: false,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('hrWingHeadSetup.edit', {
                parent: 'hrWingHeadSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/wingHeadSetup/hrWingHeadSetup-dialog.html',
                        controller: 'HrWingHeadSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrWingHeadSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrWingHeadSetup', function($stateParams, HrWingHeadSetup) {
                        return HrWingHeadSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrWingHeadSetup.newwh', {
                parent: 'hrWingHeadSetup',
                url: '/{wingid}/newwh',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/wingHeadSetup/hrWingHeadSetup-dialog.html',
                        controller: 'HrWingHeadSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrWingHeadSetup');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            joinDate: null,
                            endDate: null,
                            activeHead: false,
                            activeStatus: false,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            });
    });
