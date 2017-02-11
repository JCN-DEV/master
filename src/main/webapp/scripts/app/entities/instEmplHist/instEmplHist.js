'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instEmplHist', {
                parent: 'entity',
                url: '/instEmplHists',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplHist.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplHist/instEmplHists.html',
                        controller: 'InstEmplHistController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplHist');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instEmplHist.detail', {
                parent: 'entity',
                url: '/instEmplHist/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplHist.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplHist/instEmplHist-detail.html',
                        controller: 'InstEmplHistDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplHist');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmplHist', function($stateParams, InstEmplHist) {
                        return InstEmplHist.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instEmplHist.new', {
                parent: 'instEmplHist',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplHist/instEmplHist-dialog.html',
                        controller: 'InstEmplHistDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    designation: null,
                                    jobArea: null,
                                    start: null,
                                    end: null,
                                    onTrack: null,
                                    telephone: null,
                                    ext: null,
                                    email: null,
                                    mobile: null,
                                    website: null,
                                    certificateCopy: null,
                                    certificateCopyContentType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplHist', null, { reload: true });
                    }, function() {
                        $state.go('instEmplHist');
                    })
                }]
            })
            .state('instEmplHist.edit', {
                parent: 'instEmplHist',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplHist/instEmplHist-dialog.html',
                        controller: 'InstEmplHistDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmplHist', function(InstEmplHist) {
                                return InstEmplHist.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplHist', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instEmplHist.delete', {
                parent: 'instEmplHist',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplHist/instEmplHist-delete-dialog.html',
                        controller: 'InstEmplHistDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmplHist', function(InstEmplHist) {
                                return InstEmplHist.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplHist', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
