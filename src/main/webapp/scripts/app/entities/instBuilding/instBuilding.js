'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
           /* .state('infrastructureInfo', {
                parent: 'entity',
                url: '/infrastructureInfo',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instBuilding.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instBuilding/instBuildings.html',
                        controller: 'InstBuildingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instBuilding');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })*/
            .state('instGenInfo.infrastructureInfo.detail', {
                parent: 'instGenInfo',
                url: '/infrastructureInfo/{id}',
                data: {
                    //authorities: ['ROLE_USER'],
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instBuilding.detail.title'
                },
                views: {
                    'instituteView@instGentInfo': {
                        templateUrl: 'scripts/app/entities/instBuilding/instBuilding-detail.html',
                        controller: 'InstBuildingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instBuilding');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstBuilding', function($stateParams, InstBuilding) {
                        return InstBuilding.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instGenInfo.infrastructureInfo.new', {
                parent: 'instGenInfo',
                url: '/newInfrastructureInfo',
                data: {
                    //authorities: ['ROLE_USER'],
                    authorities: [],
                },
                views: {
                    'instituteView@instGenInfo': {
                        templateUrl: 'scripts/app/entities/instBuilding/instBuilding-dialog.html',
                        controller: 'InstBuildingDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            totalArea: null,
                            totalRoom: null,
                            classRoom: null,
                            officeRoom: null,
                            otherRoom: null,
                            status: null,
                            id: null
                        };
                    }
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instBuilding/instBuilding-dialog.html',
                        controller: 'InstBuildingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    totalArea: null,
                                    totalRoom: null,
                                    classRoom: null,
                                    officeRoom: null,
                                    otherRoom: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.infrastructureInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.infrastructureInfo');
                    })
                }]*/
            })
            .state('infrastructureInfo.edit', {
                parent: 'instBuilding',
                url: '/{id}/editInfrastructureInfo',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'instituteView@instGentInfo': {
                        templateUrl: 'scripts/app/entities/instBuilding/instBuilding-dialog.html',
                        controller: 'InstBuildingDialogController'
                    }
                },
                resolve: {
                    entity: ['InstBuilding', function(InstBuilding) {
                        return InstBuilding.get({id : $stateParams.id});
                    }]
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instBuilding/instBuilding-dialog.html',
                        controller: 'InstBuildingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstBuilding', function(InstBuilding) {
                                return InstBuilding.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('infrastructureInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('infrastructureInfo.delete', {
                parent: 'instBuilding',
                url: '/{id}/deleteInfrastructureInfo',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instBuilding/instBuilding-delete-dialog.html',
                        controller: 'InstBuildingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstBuilding', function(InstBuilding) {
                                return InstBuilding.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.infrastructureInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.infrastructureInfo');
                    })
                }]
            });
    });
