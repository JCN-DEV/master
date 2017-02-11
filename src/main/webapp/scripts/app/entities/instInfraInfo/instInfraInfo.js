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
            .state('instGenInfo.instInfraInfo.detail', {
                parent: 'instGenInfo',
                url: '/infrastructure-info/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instInfraInfo.detail.title'
                },
                views: {
                    'instituteView@instGenInfo':{
                          templateUrl: 'scripts/app/entities/instInfraInfo/instInfraInfo-detail.html',
                          controller: 'InstInfraInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instInfraInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstInfraInfo', function($stateParams, InstInfraInfo) {
                        return InstInfraInfo.get({id : $stateParams.id});
                    }]
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {

                }]
            })
            .state('instGenInfo.instInfraInfo.new', {
                parent: 'instGenInfo',
                url: '/new-infrastructure-info',
                data: {
                   // authorities: ['ROLE_USER'],
                    authorities: [],
                },
                views: {
                    'instituteView@instGenInfo':{
                          templateUrl: 'scripts/app/entities/instInfraInfo/instInfraInfo-dialog.html',
                          controller: 'InstInfraInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instInfraInfo');
                        $translatePartialLoader.addPart('instBuilding');
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
            .state('instGenInfo.instInfraInfo.edit', {
                parent: 'instGenInfo',
                url: '/edit-infrastructure-info/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'instituteView@instGenInfo':{
                        templateUrl: 'scripts/app/entities/instInfraInfo/instInfraInfo-dialog.html',
                        controller: 'InstInfraInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instInfraInfo');
                        $translatePartialLoader.addPart('instBuilding');
                        $translatePartialLoader.addPart('instLand');

                        return $translate.refresh();
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
            .state('instGenInfo.instInfraInfo.delete', {
                parent: 'instGenInfo.instInfraInfo',
                url: '/{id}/delete-infrastructure-info',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instInfraInfo/instInfraInfo-delete-dialog.html',
                        controller: 'InstInfraInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstInfraInfo', function(InstInfraInfo) {
                                return InstInfraInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.instInfraInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.instInfraInfo');
                    })
                }]
            });
    });
