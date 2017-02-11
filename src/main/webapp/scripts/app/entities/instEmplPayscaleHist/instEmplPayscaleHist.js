'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instEmplPayscaleHist', {
                parent: 'entity',
                url: '/instEmplPayscaleHists',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplPayscaleHist.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplPayscaleHist/instEmplPayscaleHists.html',
                        controller: 'InstEmplPayscaleHistController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplPayscaleHist');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instEmplPayscaleHist.detail', {
                parent: 'entity',
                url: '/instEmplPayscaleHist/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplPayscaleHist.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplPayscaleHist/instEmplPayscaleHist-detail.html',
                        controller: 'InstEmplPayscaleHistDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplPayscaleHist');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmplPayscaleHist', function($stateParams, InstEmplPayscaleHist) {
                        return InstEmplPayscaleHist.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instEmplPayscaleHist.new', {
                parent: 'instEmplPayscaleHist',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplPayscaleHist/instEmplPayscaleHist-dialog.html',
                        controller: 'InstEmplPayscaleHistDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    activationDate: null,
                                    endDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplPayscaleHist', null, { reload: true });
                    }, function() {
                        $state.go('instEmplPayscaleHist');
                    })
                }]
            })
            .state('instEmplPayscaleHist.edit', {
                parent: 'instEmplPayscaleHist',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplPayscaleHist/instEmplPayscaleHist-dialog.html',
                        controller: 'InstEmplPayscaleHistDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmplPayscaleHist', function(InstEmplPayscaleHist) {
                                return InstEmplPayscaleHist.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplPayscaleHist', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instEmplPayscaleHist.delete', {
                parent: 'instEmplPayscaleHist',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplPayscaleHist/instEmplPayscaleHist-delete-dialog.html',
                        controller: 'InstEmplPayscaleHistDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmplPayscaleHist', function(InstEmplPayscaleHist) {
                                return InstEmplPayscaleHist.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplPayscaleHist', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
