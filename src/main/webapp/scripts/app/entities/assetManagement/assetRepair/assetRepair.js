'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('assetRepair', {
                parent: 'assetManagements',
                url: '/assetRepairs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetRepair.home.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetRepair/assetRepairs.html',
                        controller: 'AssetRepairController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetRepair');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('assetRepair.detail', {
                parent: 'assetManagements',
                url: '/assetRepair/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetRepair.detail.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetRepair/assetRepair-detail.html',
                        controller: 'AssetRepairDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetRepair');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetRepair', function($stateParams, AssetRepair) {
                        return AssetRepair.get({id : $stateParams.id});
                    }]
                }
            })
            .state('assetRepair.new', {
                parent: 'assetRepair',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetRepair/assetRepair-dialog.html',
                        controller: 'AssetRepairDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetRepair');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetRepair', function($stateParams, AssetRepair) {
                        return null;
                    }]
                }
                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetRepair/assetRepair-dialog.html',
                //        controller: 'AssetRepairDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: function () {
                //                return {
                //                    refNo: null,
                //                    repairedBy: null,
                //                    dateOfProblem: null,
                //                    repairDate: null,
                //                    repairCost: null,
                //                    id: null
                //                };
                //            }
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetRepair', null, { reload: true });
                //    }, function() {
                //        $state.go('assetRepair');
                //    })
                //}]
            })
            .state('assetRepair.edit', {
                parent: 'assetRepair',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetRepair/assetRepair-dialog.html',
                        controller: 'AssetRepairDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetRepair');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetRepair', function($stateParams, AssetRepair) {
                        return AssetRepair.get({id : $stateParams.id});
                    }]
                }
                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetRepair/assetRepair-dialog.html',
                //        controller: 'AssetRepairDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: ['AssetRepair', function(AssetRepair) {
                //                return AssetRepair.get({id : $stateParams.id});
                //            }]
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetRepair', null, { reload: true });
                //    }, function() {
                //        $state.go('^');
                //    })
                //}]
            });
    });
