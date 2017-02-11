'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cat', {
                parent: 'entity',
                url: '/category',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.cat.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cat/cats.html',
                        controller: 'CatController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cat');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cat.detail', {
                parent: 'entity',
                url: '/category/details/{id}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.cat.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cat/cat-detail.html',
                        controller: 'CatDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cat');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cat', function($stateParams, Cat) {
                        return Cat.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cat.new', {
                parent: 'entity',
                url: '/category/new',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.cat.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cat/cat-form.html',
                        controller: 'CatFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cat');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', function($stateParams) {

                    }]
                }
            })
            .state('cat.edit', {
                parent: 'cat',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_EMPLOYER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cat/cat-form.html',
                        controller: 'CatFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cat');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cat', function($stateParams, Cat) {
                        return Cat.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cat.delete', {
                parent: 'cat',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_EMPLOYER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cat/cat-delete-dialog.html',
                        controller: 'CatDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cat', function(Cat) {
                                return Cat.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cat', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
