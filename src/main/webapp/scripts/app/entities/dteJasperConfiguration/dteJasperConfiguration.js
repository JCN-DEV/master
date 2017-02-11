'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dteJasperConfiguration', {
                parent: 'entity',
                url: '/dteJasperConfigurations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dteJasperConfiguration.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dteJasperConfiguration/dteJasperConfigurations.html',
                        controller: 'DteJasperConfigurationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dteJasperConfiguration');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dteJasperConfiguration.detail', {
                parent: 'entity',
                url: '/dteJasperConfiguration/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dteJasperConfiguration.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dteJasperConfiguration/dteJasperConfiguration-detail.html',
                        controller: 'DteJasperConfigurationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dteJasperConfiguration');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DteJasperConfiguration', function($stateParams, DteJasperConfiguration) {
                        return DteJasperConfiguration.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dteJasperConfiguration.new', {
                parent: 'dteJasperConfiguration',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dteJasperConfiguration/dteJasperConfiguration-dialog.html',
                        controller: 'DteJasperConfigurationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    domain: null,
                                    port: null,
                                    username: null,
                                    password: null,
                                    createUrl: null,
                                    createDate: null,
                                    modifiedDate: null,
                                    createBy: null,
                                    modifiedBy: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dteJasperConfiguration', null, { reload: true });
                    }, function() {
                        $state.go('dteJasperConfiguration');
                    })
                }]
            })
            .state('dteJasperConfiguration.edit', {
                parent: 'dteJasperConfiguration',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dteJasperConfiguration/dteJasperConfiguration-dialog.html',
                        controller: 'DteJasperConfigurationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DteJasperConfiguration', function(DteJasperConfiguration) {
                                return DteJasperConfiguration.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dteJasperConfiguration', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
