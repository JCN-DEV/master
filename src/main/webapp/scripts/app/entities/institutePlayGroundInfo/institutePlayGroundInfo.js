'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('institutePlayGroundInfo', {
                parent: 'entity',
                url: '/institutePlayGroundInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.institutePlayGroundInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/institutePlayGroundInfo/institutePlayGroundInfos.html',
                        controller: 'InstitutePlayGroundInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('institutePlayGroundInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('institutePlayGroundInfo.detail', {
                parent: 'entity',
                url: '/institutePlayGroundInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.institutePlayGroundInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/institutePlayGroundInfo/institutePlayGroundInfo-detail.html',
                        controller: 'InstitutePlayGroundInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('institutePlayGroundInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstitutePlayGroundInfo', function($stateParams, InstitutePlayGroundInfo) {
                        return InstitutePlayGroundInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('institutePlayGroundInfo.new', {
                parent: 'institutePlayGroundInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/institutePlayGroundInfo/institutePlayGroundInfo-dialog.html',
                        controller: 'InstitutePlayGroundInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    playgroundNo: null,
                                    area: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('institutePlayGroundInfo', null, { reload: true });
                    }, function() {
                        $state.go('institutePlayGroundInfo');
                    })
                }]
            })
            .state('institutePlayGroundInfo.edit', {
                parent: 'institutePlayGroundInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/institutePlayGroundInfo/institutePlayGroundInfo-dialog.html',
                        controller: 'InstitutePlayGroundInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstitutePlayGroundInfo', function(InstitutePlayGroundInfo) {
                                return InstitutePlayGroundInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('institutePlayGroundInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('institutePlayGroundInfo.delete', {
                parent: 'institutePlayGroundInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/institutePlayGroundInfo/institutePlayGroundInfo-delete-dialog.html',
                        controller: 'InstitutePlayGroundInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstitutePlayGroundInfo', function(InstitutePlayGroundInfo) {
                                return InstitutePlayGroundInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('institutePlayGroundInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
