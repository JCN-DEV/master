'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('certName', {
                parent: 'entity',
                url: '/certNames',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.certName.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/certName/certNames.html',
                        controller: 'CertNameController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('certName');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('certName.detail', {
                parent: 'entity',
                url: '/certName/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.certName.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/certName/certName-detail.html',
                        controller: 'CertNameDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('certName');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CertName', function($stateParams, CertName) {
                        return CertName.get({id : $stateParams.id});
                    }]
                }
            })
            .state('certName.new', {
                parent: 'certName',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/certName/certName-dialog.html',
                        controller: 'CertNameDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('certName', null, { reload: true });
                    }, function() {
                        $state.go('certName');
                    })
                }]
            })
            .state('certName.edit', {
                parent: 'certName',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/certName/certName-dialog.html',
                        controller: 'CertNameDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CertName', function(CertName) {
                                return CertName.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('certName', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('certName.delete', {
                parent: 'certName',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/certName/certName-delete-dialog.html',
                        controller: 'CertNameDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CertName', function(CertName) {
                                return CertName.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('certName', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
