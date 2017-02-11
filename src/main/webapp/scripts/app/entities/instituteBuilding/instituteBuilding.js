'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteBuilding', {
                parent: 'entity',
                url: '/instituteBuildings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteBuilding.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteBuilding/instituteBuildings.html',
                        controller: 'InstituteBuildingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteBuilding');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteBuilding.detail', {
                parent: 'entity',
                url: '/instituteBuilding/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteBuilding.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteBuilding/instituteBuilding-detail.html',
                        controller: 'InstituteBuildingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteBuilding');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstituteBuilding', function($stateParams, InstituteBuilding) {
                        return InstituteBuilding.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instituteBuilding.new', {
                parent: 'instituteBuilding',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteBuilding/instituteBuilding-dialog.html',
                        controller: 'InstituteBuildingDialogController',
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
                        $state.go('instituteBuilding', null, { reload: true });
                    }, function() {
                        $state.go('instituteBuilding');
                    })
                }]
            })
            .state('instituteBuilding.edit', {
                parent: 'instituteBuilding',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteBuilding/instituteBuilding-dialog.html',
                        controller: 'InstituteBuildingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstituteBuilding', function(InstituteBuilding) {
                                return InstituteBuilding.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteBuilding', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instituteBuilding.delete', {
                parent: 'instituteBuilding',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteBuilding/instituteBuilding-delete-dialog.html',
                        controller: 'InstituteBuildingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstituteBuilding', function(InstituteBuilding) {
                                return InstituteBuilding.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteBuilding', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
