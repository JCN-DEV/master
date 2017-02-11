'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instEmplWithhelHist', {
                parent: 'entity',
                url: '/instEmplWithhelHists',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplWithhelHist.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplWithhelHist/instEmplWithhelHists.html',
                        controller: 'InstEmplWithhelHistController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplWithhelHist');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instEmplWithhelHist.detail', {
                parent: 'entity',
                url: '/instEmplWithhelHist/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplWithhelHist.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplWithhelHist/instEmplWithhelHist-detail.html',
                        controller: 'InstEmplWithhelHistDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplWithhelHist');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmplWithhelHist', function($stateParams, InstEmplWithhelHist) {
                        return InstEmplWithhelHist.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instEmplWithhelHist.new', {
                parent: 'instEmplWithhelHist',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplWithhelHist/instEmplWithhelHist-dialog.html',
                        controller: 'InstEmplWithhelHistDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    withheldAmount: null,
                                    startDate: null,
                                    stopDate: null,
                                    createdDate: null,
                                    modifiedDate: null,
                                    status: null,
                                    remark: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplWithhelHist', null, { reload: true });
                    }, function() {
                        $state.go('instEmplWithhelHist');
                    })
                }]
            })
            .state('instEmplWithhelHist.edit', {
                parent: 'instEmplWithhelHist',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplWithhelHist/instEmplWithhelHist-dialog.html',
                        controller: 'InstEmplWithhelHistDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmplWithhelHist', function(InstEmplWithhelHist) {
                                return InstEmplWithhelHist.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplWithhelHist', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instEmplWithhelHist.delete', {
                parent: 'instEmplWithhelHist',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplWithhelHist/instEmplWithhelHist-delete-dialog.html',
                        controller: 'InstEmplWithhelHistDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmplWithhelHist', function(InstEmplWithhelHist) {
                                return InstEmplWithhelHist.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplWithhelHist', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
