'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instBuildingTemp', {
                parent: 'entity',
                url: '/instBuildingTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instBuildingTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instBuildingTemp/instBuildingTemps.html',
                        controller: 'InstBuildingTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instBuildingTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instBuildingTemp.detail', {
                parent: 'entity',
                url: '/instBuildingTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instBuildingTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instBuildingTemp/instBuildingTemp-detail.html',
                        controller: 'InstBuildingTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instBuildingTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstBuildingTemp', function($stateParams, InstBuildingTemp) {
                        return InstBuildingTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instBuildingTemp.new', {
                parent: 'instBuildingTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instBuildingTemp/instBuildingTemp-dialog.html',
                        controller: 'InstBuildingTempDialogController',
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
                        $state.go('instBuildingTemp', null, { reload: true });
                    }, function() {
                        $state.go('instBuildingTemp');
                    })
                }]
            })
            .state('instBuildingTemp.edit', {
                parent: 'instBuildingTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instBuildingTemp/instBuildingTemp-dialog.html',
                        controller: 'InstBuildingTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstBuildingTemp', function(InstBuildingTemp) {
                                return InstBuildingTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instBuildingTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
