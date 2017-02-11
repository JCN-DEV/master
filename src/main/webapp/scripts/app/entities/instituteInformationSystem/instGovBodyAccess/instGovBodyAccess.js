'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instGovBodyAccess', {
                parent: 'instituteInfo',
                url: '/instGovBodyAccesss',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instGovBodyAccess.home.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instGovBodyAccess/instGovBodyAccesss.html',
                        controller: 'InstGovBodyAccessController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGovBodyAccess');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instGovBodyAccess.detail', {
                parent: 'instituteInfo',
                url: '/instGovBodyAccess/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instGovBodyAccess.detail.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instGovBodyAccess/instGovBodyAccess-detail.html',
                        controller: 'InstGovBodyAccessDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGovBodyAccess');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstGovBodyAccess', function($stateParams, InstGovBodyAccess) {
                        return InstGovBodyAccess.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instGovBodyAccess.new', {
                parent: 'instituteInfo',
                url: '/newGovBody',
                data: {
                    authorities: [],
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instGovBodyAccess/instGovBodyAccess-dialog.html',
                        controller: 'InstGovBodyAccessDialogController',
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGovBodyAccess');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstGovBodyAccess', function($stateParams, InstGovBodyAccess) {
                        return InstGovBodyAccess.get({id : $stateParams.id});
                    }]
                }
            })/*

                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGovBodyAccess/instGovBodyAccess-dialog.html',
                        controller: 'InstGovBodyAccessDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    dateCreated: null,
                                    dateModified: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instGovBodyAccess', null, { reload: true });
                    }, function() {
                        $state.go('instGovBodyAccess');
                    })
                }]
            })*/
            .state('instGovBodyAccess.edit', {
                parent: 'instituteInfo',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instGovBodyAccess/instGovBodyAccess-dialog.html',
                        controller: 'InstGovBodyAccessDialogController',
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGovBodyAccess');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstGovBodyAccess', function($stateParams, InstGovBodyAccess) {
                        return InstGovBodyAccess.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instGovBodyAccess.delete', {
                parent: 'instituteInfo',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instGovBodyAccess/instGovBodyAccess-delete-dialog.html',
                        controller: 'InstGovBodyAccessDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstGovBodyAccess', function(InstGovBodyAccess) {
                                return InstGovBodyAccess.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGovBodyAccess', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
