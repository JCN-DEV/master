'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instEmplDesignation', {
                parent: 'entity',
                url: '/instEmplDesignations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplDesignation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplDesignation/instEmplDesignations.html',
                        controller: 'InstEmplDesignationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplDesignation');
                        $translatePartialLoader.addPart('designationType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instEmplDesignation.detail', {
                parent: 'entity',
                url: '/instEmplDesignation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplDesignation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplDesignation/instEmplDesignation-detail.html',
                        controller: 'InstEmplDesignationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplDesignation');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmplDesignation', function($stateParams, InstEmplDesignation) {
                        return InstEmplDesignation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instEmplDesignation.new', {
                parent: 'instEmplDesignation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplDesignation/instEmplDesignation-dialog.html',
                        controller: 'InstEmplDesignationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    type: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplDesignation', null, { reload: true });
                    }, function() {
                        $state.go('instEmplDesignation');
                    })
                }]
            })
            .state('instEmplDesignation.edit', {
                parent: 'instEmplDesignation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplDesignation/instEmplDesignation-dialog.html',
                        controller: 'InstEmplDesignationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmplDesignation', function(InstEmplDesignation) {
                                return InstEmplDesignation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplDesignation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instEmplDesignation.delete', {
                parent: 'instEmplDesignation',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplDesignation/instEmplDesignation-delete-dialog.html',
                        controller: 'InstEmplDesignationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmplDesignation', function(InstEmplDesignation) {
                                return InstEmplDesignation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplDesignation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
