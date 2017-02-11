'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('umracRoleSetup', {
                parent: 'umrac',
                url: '/umracRoleSetups',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.umracRoleSetup.home.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracRoleSetup/umracRoleSetups.html',
                        controller: 'UmracRoleSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracRoleSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('umracRoleSetup.detail', {
                parent: 'umracRoleSetup',
                url: '/umracRoleSetup/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.umracRoleSetup.detail.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracRoleSetup/umracRoleSetup-detail.html',
                        controller: 'UmracRoleSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracRoleSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UmracRoleSetup', function ($stateParams, UmracRoleSetup) {
                        return UmracRoleSetup.get({id: $stateParams.id});
                    }]
                }
            })

            .state('umracRoleSetup.edit', {
                parent: 'umracRoleSetup',
                url: '/edit/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.umracRoleSetup.detail.title'
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracRoleSetup/umracRoleSetup-dialog.html',
                        controller: 'UmracRoleSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracRoleSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UmracRoleSetup', function ($stateParams, UmracRoleSetup) {
                        return UmracRoleSetup.get({id: $stateParams.id});
                    }]
                }
            })

            .state('umracRoleSetup.new', {
                parent: 'umracRoleSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'umracManagementView@umrac': {
                        templateUrl: 'scripts/app/entities/umrac/umracRoleSetup/umracRoleSetup-dialog.html',
                        controller: 'UmracRoleSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('umracRoleSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            roleId: null,
                            roleName: null,
                            description: null,
                            status: true,
                            createDate: null,
                            createBy: null,
                            updatedBy: null,
                            updatedTime: null,
                            id: null
                        };
                    }
                }
            });
    });
