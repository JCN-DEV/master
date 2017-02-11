'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteInfo', {
                parent: 'entity',
                url: '/instituteInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteInfo/instituteInfos.html',
                        controller: 'InstituteInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteInfo.detail', {
                parent: 'entity',
                url: '/instituteInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteInfo/instituteInfo-detail.html',
                        controller: 'InstituteInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstituteInfo', function($stateParams, InstituteInfo) {
                        return InstituteInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instituteInfo.new', {
                parent: 'instituteInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInfo/instituteInfo-dialog.html',
                        controller: 'InstituteInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    publicationDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instituteInfo', null, { reload: true });
                    }, function() {
                        $state.go('instituteInfo');
                    })
                }]
            })
            .state('instituteInfo.edit', {
                parent: 'instituteInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInfo/instituteInfo-dialog.html',
                        controller: 'InstituteInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstituteInfo', function(InstituteInfo) {
                                return InstituteInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instituteInfo.delete', {
                parent: 'instituteInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInfo/instituteInfo-delete-dialog.html',
                        controller: 'InstituteInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstituteInfo', function(InstituteInfo) {
                                return InstituteInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
