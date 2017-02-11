'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('miscConfigSetup', {
                parent: 'hrm',
                url: '/miscConfigSetups',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.miscConfigSetup.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/miscConfigSetup/miscConfigSetups.html',
                        controller: 'MiscConfigSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('miscConfigSetup');
                        $translatePartialLoader.addPart('miscConfigDataType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('miscConfigSetup.detail', {
                parent: 'hrm',
                url: '/miscConfigSetup/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.miscConfigSetup.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/miscConfigSetup/miscConfigSetup-detail.html',
                        controller: 'MiscConfigSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('miscConfigSetup');
                        $translatePartialLoader.addPart('miscConfigDataType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MiscConfigSetup', function($stateParams, MiscConfigSetup) {
                        return MiscConfigSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('miscConfigSetup.new', {
                parent: 'miscConfigSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/miscConfigSetup/miscConfigSetup-dialog.html',
                        controller: 'MiscConfigSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('miscConfigSetup');
                        $translatePartialLoader.addPart('miscConfigDataType');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            propertyName: null,
                            propertyTitle: null,
                            propertyValue: null,
                            propertyDataType: null,
                            propertyValueMax: null,
                            propertyDesc: null,
                            activeStatus: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('miscConfigSetup.edit', {
                parent: 'miscConfigSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/miscConfigSetup/miscConfigSetup-dialog.html',
                        controller: 'MiscConfigSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('miscConfigSetup');
                        $translatePartialLoader.addPart('miscConfigDataType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MiscConfigSetup', function($stateParams, MiscConfigSetup) {
                        return MiscConfigSetup.get({id : $stateParams.id});
                    }]
                }
            });
    });
