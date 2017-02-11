'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('module', {
                parent: 'entity',
                url: '/modules',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.module.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/module/modules.html',
                        controller: 'ModuleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('module');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('module.detail', {
                parent: 'entity',
                url: '/module/{id}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.module.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/module/module-detail.html',
                        controller: 'ModuleDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('module');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Module', function($stateParams, Module) {
                        return Module.get({id : $stateParams.id});
                    }]
                }
            })
            .state('module.new', {
                parent: 'module',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.module.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/module/module-dialog.html',
                        controller: 'ModuleDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('module');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Module', function($stateParams, Module) {

                    }]
                }
            })
            .state('module.edit', {
                parent: 'module',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.module.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/module/module-dialog.html',
                        controller: 'ModuleDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('module');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Module', function($stateParams, Module) {
                        return Module.get({id : $stateParams.id});
                    }]
                }
            });
    });
