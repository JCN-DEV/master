'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteGovernBody', {
                parent: 'entity',
                url: '/instituteGovernBodys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteGovernBody.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteGovernBody/instituteGovernBodys.html',
                        controller: 'InstituteGovernBodyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteGovernBody');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteGovernBody.detail', {
                parent: 'entity',
                url: '/instituteGovernBody/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteGovernBody.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteGovernBody/instituteGovernBody-detail.html',
                        controller: 'InstituteGovernBodyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteGovernBody');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstituteGovernBody', function($stateParams, InstituteGovernBody) {
                        return InstituteGovernBody.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instituteGovernBody.new', {
                parent: 'instituteGovernBody',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteGovernBody/instituteGovernBody-dialog.html',
                        controller: 'InstituteGovernBodyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    position: null,
                                    mobileNo: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instituteGovernBody', null, { reload: true });
                    }, function() {
                        $state.go('instituteGovernBody');
                    })
                }]
            })
            .state('instituteGovernBody.edit', {
                parent: 'instituteGovernBody',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteGovernBody/instituteGovernBody-dialog.html',
                        controller: 'InstituteGovernBodyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstituteGovernBody', function(InstituteGovernBody) {
                                return InstituteGovernBody.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteGovernBody', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instituteGovernBody.delete', {
                parent: 'instituteGovernBody',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteGovernBody/instituteGovernBody-delete-dialog.html',
                        controller: 'InstituteGovernBodyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstituteGovernBody', function(InstituteGovernBody) {
                                return InstituteGovernBody.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteGovernBody', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
