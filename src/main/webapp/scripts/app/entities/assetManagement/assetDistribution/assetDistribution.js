'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('assetDistribution', {
                parent: 'assetManagements',
                url: '/assetDistributions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetDistribution.home.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetDistribution/assetDistributions.html',
                        controller: 'AssetDistributionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetDistribution');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('assetDistribution.detail', {
                parent: 'assetDistribution',
                url: '/assetDistribution/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetDistribution.detail.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetDistribution/assetDistribution-detail.html',
                        controller: 'AssetDistributionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetDistribution');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetDistribution', function($stateParams, AssetDistribution) {
                        return AssetDistribution.get({id : $stateParams.id});
                    }]
                }
            })
            .state('assetDistribution.new', {
                parent: 'assetDistribution',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetDistribution.detail.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetDistribution/assetDistribution-dialog.html',
                        controller: 'AssetDistributionDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetDistribution');
                        return $translate.refresh();
                    }],
                    /*entity: ['$stateParams', 'AssetDistribution', function($stateParams, AssetDistribution) {
                        return null;
                    }]*/
                    entity: function () {
                        return {
                            transferRef: 'Auto ID',

                            assignedDdate: null,
                            employeeid: null,
                            createDate: null,
                            createBy: null,
                            updatedBy: null,
                            updatedTime: null,
                            id: null
                        };
                    }
                }

                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetDistribution/assetDistribution-dialog.html',
                //        controller: 'AssetDistributionDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: function () {
                //                return {
                //                    transferRef: null,
                //                    assignedDdate: null,
                //                    remartks: null,
                //                    id: null
                //                };
                //            }
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetDistribution', null, { reload: true });
                //    }, function() {
                //        $state.go('assetDistribution');
                //    })
                //}]
            })
            .state('assetDistribution.edit', {
                parent: 'assetDistribution',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetDistribution.detail.title'
                },


                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetDistribution/assetDistribution-dialog.html',
                        controller: 'AssetDistributionDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetDistribution');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetDistribution', function($stateParams, AssetDistribution) {
                        return AssetDistribution.get({id : $stateParams.id});
                    }]
                }

                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetDistribution/assetDistribution-dialog.html',
                //        controller: 'AssetDistributionDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: ['AssetDistribution', function(AssetDistribution) {
                //                return AssetDistribution.get({id : $stateParams.id});
                //            }]
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetDistribution', null, { reload: true });
                //    }, function() {
                //        $state.go('^');
                //    })
                //}]
            });
    });
