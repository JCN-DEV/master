'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('reference', {
                parent: 'entity',
                url: '/references',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.reference.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reference/references.html',
                        controller: 'ReferenceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reference');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('reference.detail', {
                parent: 'entity',
                url: '/reference/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.reference.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reference/reference-detail.html',
                        controller: 'ReferenceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reference');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Reference', function($stateParams, Reference) {
                        return Reference.get({id : $stateParams.id});
                    }]
                }
            })
            .state('reference.new', {
                parent: 'reference',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/reference/reference-dialog.html',
                        controller: 'ReferenceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    email: null,
                                    organisation: null,
                                    designation: null,
                                    relation: null,
                                    phone: null,
                                    remarks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('reference', null, { reload: true });
                    }, function() {
                        $state.go('reference');
                    })
                }]
            })
            .state('reference.edit', {
                parent: 'reference',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/reference/reference-dialog.html',
                        controller: 'ReferenceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Reference', function(Reference) {
                                return Reference.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('reference', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('reference.delete', {
                parent: 'reference',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/reference/reference-delete-dialog.html',
                        controller: 'ReferenceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Reference', function(Reference) {
                                return Reference.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('reference', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
