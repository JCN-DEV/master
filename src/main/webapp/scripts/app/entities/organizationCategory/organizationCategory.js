'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('organizationCategory', {
                parent: 'entity',
                url: '/organizationCategorys',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.organizationCategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organizationCategory/organizationCategorys.html',
                        controller: 'OrganizationCategoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organizationCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('organizationCategory.detail', {
                parent: 'entity',
                url: '/organizationCategory/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.organizationCategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organizationCategory/organizationCategory-detail.html',
                        controller: 'OrganizationCategoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organizationCategory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OrganizationCategory', function($stateParams, OrganizationCategory) {
                        return OrganizationCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('organizationCategory.new', {
                parent: 'organizationCategory',
                url: '/new',
                /*data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/organizationCategory/organizationCategory-dialog.html',
                        controller: 'OrganizationCategoryDialogController',
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
                        $state.go('organizationCategory', null, { reload: true });
                    }, function() {
                        $state.go('organizationCategory');
                    })
                }]
            })*/
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.organizationCategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organizationCategory/organizationCategory-dialog.html',
                        controller: 'OrganizationCategoryDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organizationCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OrganizationCategory', function($stateParams, OrganizationCategory) {
                        return null;
                    }]
                }
            })
            .state('organizationCategory.edit', {
                parent: 'organizationCategory',
                url: '/{id}/edit',
                /*data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/organizationCategory/organizationCategory-dialog.html',
                        controller: 'OrganizationCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['OrganizationCategory', function(OrganizationCategory) {
                                return OrganizationCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('organizationCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.organizationCategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organizationCategory/organizationCategory-dialog.html',
                        controller: 'OrganizationCategoryDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organizationCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OrganizationCategory', function($stateParams, OrganizationCategory) {
                        return OrganizationCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('organizationCategory.delete', {
                parent: 'organizationCategory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/organizationCategory/organizationCategory-delete-dialog.html',
                        controller: 'OrganizationCategoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['OrganizationCategory', function(OrganizationCategory) {
                                return OrganizationCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('organizationCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
