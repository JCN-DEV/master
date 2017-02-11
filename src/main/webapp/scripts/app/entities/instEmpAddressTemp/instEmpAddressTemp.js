'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instEmpAddressTemp', {
                parent: 'entity',
                url: '/instEmpAddressTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmpAddressTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmpAddressTemp/instEmpAddressTemps.html',
                        controller: 'InstEmpAddressTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpAddressTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instEmpAddressTemp.detail', {
                parent: 'entity',
                url: '/instEmpAddressTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmpAddressTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmpAddressTemp/instEmpAddressTemp-detail.html',
                        controller: 'InstEmpAddressTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpAddressTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmpAddressTemp', function($stateParams, InstEmpAddressTemp) {
                        return InstEmpAddressTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instEmpAddressTemp.new', {
                parent: 'instEmpAddressTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpAddressTemp/instEmpAddressTemp-dialog.html',
                        controller: 'InstEmpAddressTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    villageOrHouse: null,
                                    roadBlockSector: null,
                                    post: null,
                                    prevVillageOrHouse: null,
                                    prevRoadBlockSector: null,
                                    prevPost: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpAddressTemp', null, { reload: true });
                    }, function() {
                        $state.go('instEmpAddressTemp');
                    })
                }]
            })
            .state('instEmpAddressTemp.edit', {
                parent: 'instEmpAddressTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpAddressTemp/instEmpAddressTemp-dialog.html',
                        controller: 'InstEmpAddressTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmpAddressTemp', function(InstEmpAddressTemp) {
                                return InstEmpAddressTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpAddressTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
