'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('deo', {
                parent: 'entity',
                url: '/deos',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.deo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deo/deos.html',
                        controller: 'DeoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('deo.detail', {
                parent: 'entity',
                url: '/deo/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.deo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deo/deo-detail.html',
                        controller: 'DeoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Deo', function($stateParams, Deo) {
                        return Deo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('deo.new', {
                parent: 'deo',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deo/deo-dialog.html',
                        controller: 'DeoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deo');
                        return $translate.refresh();
                    }]
                   /* entity: ['$stateParams', 'Deo', function($stateParams, Deo) {
                        return Deo.get({id : $stateParams.id});
                    }]*/
                }

                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/deo/deo-dialog.html',
                        controller: 'DeoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    contactNo: null,
                                    name: null,
                                    designation: null,
                                    orgName: null,
                                    dateCrated: null,
                                    dateModified: null,
                                    status: null,
                                    email: null,
                                    activated: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('deo', null, { reload: true });
                    }, function() {
                        $state.go('deo');
                    })
                }]*/
            })
            .state('deo.edit', {
                parent: 'deo',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deo/deo-dialog.html',
                        controller: 'DeoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('deo.delete', {
                parent: 'deo',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/deo/deo-delete-dialog.html',
                        controller: 'DeoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Deo', function(Deo) {
                                return Deo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('deo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
