'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('externalCV', {
                parent: 'entity',
                url: '/externalCVs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.externalCV.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/externalCV/externalCVs.html',
                        controller: 'ExternalCVController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('externalCV');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('externalCV.detail', {
                parent: 'entity',
                url: '/externalCV/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.externalCV.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/externalCV/externalCV-detail.html',
                        controller: 'ExternalCVDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('externalCV');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ExternalCV', function($stateParams, ExternalCV) {
                        return ExternalCV.get({id : $stateParams.id});
                    }]
                }
            })
            .state('externalCV.new', {
                parent: 'externalCV',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/externalCV/externalCV-dialog.html',
                        controller: 'ExternalCVDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    cv: null,
                                    cvContentType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('externalCV', null, { reload: true });
                    }, function() {
                        $state.go('externalCV');
                    })
                }]
            })
            .state('externalCV.edit', {
                parent: 'externalCV',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/externalCV/externalCV-dialog.html',
                        controller: 'ExternalCVDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ExternalCV', function(ExternalCV) {
                                return ExternalCV.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('externalCV', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
