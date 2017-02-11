'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cmsSubAssign', {
                parent: 'entity',
                url: '/cmsSubAssigns',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.cmsSubAssign.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsSubAssign/cmsSubAssigns.html',
                        controller: 'CmsSubAssignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSubAssign');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cmsSubAssign.detail', {
                parent: 'entity',
                url: '/cmsSubAssign/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.cmsSubAssign.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsSubAssign/cmsSubAssign-detail.html',
                        controller: 'CmsSubAssignDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSubAssign');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CmsSubAssign', function($stateParams, CmsSubAssign) {
                        return CmsSubAssign.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cmsSubAssign.new', {
                parent: 'cmsSubAssign',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSubAssign/cmsSubAssign-dialog.html',
                        controller: 'CmsSubAssignDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    subject: null,
                                    description: null,
                                    examFee: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSubAssign', null, { reload: true });
                    }, function() {
                        $state.go('cmsSubAssign');
                    })
                }]
            })
            .state('cmsSubAssign.edit', {
                parent: 'cmsSubAssign',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSubAssign/cmsSubAssign-dialog.html',
                        controller: 'CmsSubAssignDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CmsSubAssign', function(CmsSubAssign) {
                                return CmsSubAssign.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSubAssign', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cmsSubAssign.delete', {
                parent: 'cmsSubAssign',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSubAssign/cmsSubAssign-delete-dialog.html',
                        controller: 'CmsSubAssignDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CmsSubAssign', function(CmsSubAssign) {
                                return CmsSubAssign.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSubAssign', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
