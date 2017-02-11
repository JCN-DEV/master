'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('organizationType', {
                parent: 'entity',
                url: '/organizationTypes',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.organizationType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organizationType/organizationTypes.html',
                        controller: 'OrganizationTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organizationType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('organizationType.detail', {
                parent: 'entity',
                url: '/organizationType/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.organizationType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organizationType/organizationType-detail.html',
                        controller: 'OrganizationTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organizationType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OrganizationType', function($stateParams, OrganizationType) {
                        return OrganizationType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('organizationType.new', {
                parent: 'organizationType',
                url: '/new',
                /*data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/organizationType/organizationType-dialog.html',
                        controller: 'OrganizationTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('organizationType', null, { reload: true });
                    }, function() {
                        $state.go('organizationType');
                    })
                }]*/
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.organizationType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organizationType/organizationType-dialog.html',
                        controller: 'OrganizationTypeDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organizationType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['OrganizationType', function(OrganizationType) {
                     return null;
                     }]
                }
            })
            .state('organizationType.edit', {
                parent: 'organizationType',
                url: '/{id}/edit',
                /*data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/organizationType/organizationType-dialog.html',
                        controller: 'OrganizationTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['OrganizationType', function(OrganizationType) {
                                return OrganizationType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('organizationType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.organizationType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organizationType/organizationType-dialog.html',
                        controller: 'OrganizationTypeDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organizationType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OrganizationType', function($stateParams, OrganizationType) {
                        return OrganizationType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('organizationType.delete', {
                parent: 'organizationType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/organizationType/organizationType-delete-dialog.html',
                        controller: 'OrganizationTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['OrganizationType', function(OrganizationType) {
                                return OrganizationType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('organizationType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
