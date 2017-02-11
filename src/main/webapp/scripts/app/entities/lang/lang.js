'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lang', {
                parent: 'entity',
                url: '/langs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.lang.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lang/langs.html',
                        controller: 'LangController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lang');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('lang.detail', {
                parent: 'entity',
                url: '/lang/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.lang.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lang/lang-detail.html',
                        controller: 'LangDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lang');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Lang', function($stateParams, Lang) {
                        return Lang.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lang.new', {
                parent: 'lang',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/lang/lang-dialog.html',
                        controller: 'LangDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    reading: null,
                                    writing: null,
                                    speaking: null,
                                    listening: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('lang', null, { reload: true });
                    }, function() {
                        $state.go('lang');
                    })
                }]
            })
            .state('lang.edit', {
                parent: 'lang',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/lang/lang-dialog.html',
                        controller: 'LangDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Lang', function(Lang) {
                                return Lang.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('lang', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('lang.delete', {
                parent: 'lang',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/lang/lang-delete-dialog.html',
                        controller: 'LangDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Lang', function(Lang) {
                                return Lang.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('lang', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
