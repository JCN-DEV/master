'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mpoApplication', {
                parent: 'entity',
                url: '/mpoApplications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoApplication.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mpoApplication/mpoApplications.html',
                        controller: 'MpoApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoApplication');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoApplication.detail', {
                parent: 'entity',
                url: '/mpoApplication/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoApplication.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mpoApplication/mpoApplication-detail.html',
                        controller: 'MpoApplicationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoApplication');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoApplication', function($stateParams, MpoApplication) {
                        return MpoApplication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpoApplication.new', {
                parent: 'mpoApplication',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoApplication/mpoApplication-dialog.html',
                        controller: 'MpoApplicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('mpoApplication', null, { reload: true });
                    }, function() {
                        $state.go('mpoApplication');
                    })
                }]
            })
            .state('mpoApplication.edit', {
                parent: 'mpoApplication',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoApplication/mpoApplication-dialog.html',
                        controller: 'MpoApplicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MpoApplication', function(MpoApplication) {
                                return MpoApplication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoApplication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
