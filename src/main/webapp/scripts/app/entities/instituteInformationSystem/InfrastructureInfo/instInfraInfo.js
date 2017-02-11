'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            /*.state('instInfraInfo', {
                parent: 'entity',
                url: '/instInfraInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instInfraInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instInfraInfo/instInfraInfos.html',
                        controller: 'InstInfraInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instInfraInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })*/
            .state('instituteInfo.infrastructureInfo', {
                parent: 'instituteInfo',
                url: '/infrastructure-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instInfraInfo.detail.title'
                },
                views: {
                    'instituteView@instituteInfo':{
                          templateUrl: 'scripts/app/entities/instituteInformationSystem/InfrastructureInfo/instInfraInfo-detail.html',
                          controller: 'InstInfraInfoDetailController'


                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instInfraInfo');
                        $translatePartialLoader.addPart('instBuilding');
                        $translatePartialLoader.addPart('instLand');
                        $translatePartialLoader.addPart('instShopInfo');
                        $translatePartialLoader.addPart('instPlayGroundInfo');
                        $translatePartialLoader.addPart('instLabInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', function($stateParams) {
                        //return InstInfraInfo.get({id : $stateParams.id});
                        return null;
                    }]
                }
            })
            .state('instituteInfo.infrastructureInfo.new', {
                parent: 'instituteInfo.infrastructureInfo',
                url: '/new',
                data: {
                   // authorities: ['ROLE_USER'],
                    authorities: []
                },
                views: {
                    'instituteView@instituteInfo':{
                          templateUrl: 'scripts/app/entities/instituteInformationSystem/InfrastructureInfo/instInfraInfo-dialog.html',
                          controller: 'InstInfraInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instInfraInfo');
                        $translatePartialLoader.addPart('instBuilding');
                        $translatePartialLoader.addPart('instShopInfo');
                        $translatePartialLoader.addPart('instLabInfo');
                        $translatePartialLoader.addPart('instPlayGroundInfo');
                        $translatePartialLoader.addPart('instLand');

                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            status: null,
                            id: null,
                            instBuilding:null,
                            instLand: null
                        };
                    }
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instInfraInfo/instInfraInfo-dialog.html',
                        controller: 'InstInfraInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instInfraInfo', null, { reload: true });
                    }, function() {
                        $state.go('instInfraInfo');
                    })
                }]*/
            })
            .state('instituteInfo.infrastructureInfo.edit', {
                parent: 'instituteInfo.infrastructureInfo',
                url: '/edit/{id}',
                data: {
                    authorities: []
                },
                views: {
                    'instituteView@instituteInfo':{
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/InfrastructureInfo/instInfraInfo-dialog.html',
                        controller: 'InstInfraInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instInfraInfo');
                        $translatePartialLoader.addPart('instBuilding');
                        $translatePartialLoader.addPart('instShopInfo');
                        $translatePartialLoader.addPart('instLabInfo');
                        $translatePartialLoader.addPart('instPlayGroundInfo');
                        $translatePartialLoader.addPart('instLand');

                        return $translate.refresh();
                    }],
                    entity: ['InstInfraInfoTemp','$stateParams', function(InstInfraInfoTemp,$stateParams) {
                        return InstInfraInfoTemp.get({id : $stateParams.id});
                    }]
                }
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instInfraInfo/instInfraInfo-dialog.html',
                        controller: 'InstInfraInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstInfraInfo', function(InstInfraInfo) {
                                return InstInfraInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instInfraInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('instituteInfo.infrastructureInfo.delete', {
                parent: 'instituteInfo.infrastructureInfo',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/InfrastructureInfo/instInfraInfo-delete-dialog.html',
                        controller: 'InstInfraInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstInfraInfo', function(InstInfraInfo) {
                                return InstInfraInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteInfo.infrastructureInfo', null, { reload: true });
                    }, function() {
                        $state.go('instituteInfo.infrastructureInfo');
                    })
                }]
            })

            .state('instituteInfo.infrastructureInfo.approve', {
                            parent: 'instituteInfo.approve',
                            url: '/infrastructure-info-approve/{infraId}',
                            data: {
                               // authorities: ['ROLE_ADMIN'],
                                authorities: []
                            },
                            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                                $modal.open({
                                    templateUrl: 'scripts/app/entities/instituteInformationSystem/InfrastructureInfo/instInfraInfo-approve-dialog.html',
                                    controller: 'InstInfraInfoApproveController',
                                    size: 'md',
                                    resolve: {
                                        entity: ['InstInfraInfo', function(InstInfraInfo) {
                                            return InstInfraInfo.get({id : $stateParams.infraId});
                                        }]
                                    }
                                }).result.then(function(result) {
                                    console.log(result);
                                        $state.go('instituteInfo.approve',{},{reload:true});
                                }, function() {
                                        $state.go('instituteInfo.approve',{},{reload:true});
                                })

                            }]

                        })
            .state('instituteInfo.infrastructureInfo.decline', {
                parent: 'instituteInfo.approve',
                url: '/{id}/infrastructure-info-decline',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-decline.html',
                        controller: 'InstInfraInfoDeclineController',
                        size: 'md'
                    }).result.then(function(result) {
                            console.log(result);
                            $state.go('instituteInfo.approve', null, { reload: true });
                        }, function() {
                            $state.go('instituteInfo.approve');
                        })

                }]

            })
    });
