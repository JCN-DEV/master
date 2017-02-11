'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instCategory', {
                parent: 'entity',
                url: '/instCategorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instCategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instCategory/instCategorys.html',
                        controller: 'InstCategoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instCategory.detail', {
                parent: 'entity',
                url: '/instCategory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instCategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instCategory/instCategory-detail.html',
                        controller: 'InstCategoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instCategory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstCategory', function($stateParams, InstCategory) {
                        return InstCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instCategory.new', {
                parent: 'instCategory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instCategory/instCategory-dialog.html',
                        controller: 'InstCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    pStatus: null,
                                    createdDate: null,
                                    updatedDate: null,
                                    createdBy: null,
                                    updatedBy: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instCategory', null, { reload: true });
                    }, function() {
                        $state.go('instCategory');
                    })
                }]
            })
            .state('instCategory.edit', {
                parent: 'instCategory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instCategory/instCategory-dialog.html',
                        controller: 'InstCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstCategory', function(InstCategory) {
                                return InstCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instCategory.delete', {
                parent: 'instCategory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instCategory/instCategory-delete-dialog.html',
                        controller: 'InstCategoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstCategory', function(InstCategory) {
                                return InstCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
