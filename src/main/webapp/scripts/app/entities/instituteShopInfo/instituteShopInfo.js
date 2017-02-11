'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteShopInfo', {
                parent: 'entity',
                url: '/instituteShopInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteShopInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteShopInfo/instituteShopInfos.html',
                        controller: 'InstituteShopInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteShopInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteShopInfo.detail', {
                parent: 'entity',
                url: '/instituteShopInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteShopInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteShopInfo/instituteShopInfo-detail.html',
                        controller: 'InstituteShopInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteShopInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstituteShopInfo', function($stateParams, InstituteShopInfo) {
                        return InstituteShopInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instituteShopInfo.new', {
                parent: 'instituteShopInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteShopInfo/instituteShopInfo-dialog.html',
                        controller: 'InstituteShopInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nameOrNumber: null,
                                    buildingNameOrNumber: null,
                                    length: null,
                                    width: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instituteShopInfo', null, { reload: true });
                    }, function() {
                        $state.go('instituteShopInfo');
                    })
                }]
            })
            .state('instituteShopInfo.edit', {
                parent: 'instituteShopInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteShopInfo/instituteShopInfo-dialog.html',
                        controller: 'InstituteShopInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstituteShopInfo', function(InstituteShopInfo) {
                                return InstituteShopInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteShopInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instituteShopInfo.delete', {
                parent: 'instituteShopInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteShopInfo/instituteShopInfo-delete-dialog.html',
                        controller: 'InstituteShopInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstituteShopInfo', function(InstituteShopInfo) {
                                return InstituteShopInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteShopInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
